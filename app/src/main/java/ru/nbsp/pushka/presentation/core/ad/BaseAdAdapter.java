package ru.nbsp.pushka.presentation.core.ad;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by Dimorinny on 12.06.16.
 */
public abstract class BaseAdAdapter<T extends RecyclerView.Adapter<RecyclerView.ViewHolder>>
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    static public final int VIEW_TYPE_AD = 228;

    private T mDelegateAdapter;
    private int mFrequency;

    abstract RecyclerView.ViewHolder onCreateAdViewHolder(ViewGroup parent);
    abstract void onBindAdViewHolder(RecyclerView.ViewHolder holder, int position);

    public BaseAdAdapter(T adapter) {
        mDelegateAdapter = adapter;
    }

    public void setFrequency(int mFrequency) {
        this.mFrequency = mFrequency;
    }

    public T getDelegateAdapter() {
        return mDelegateAdapter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_AD) {
            return onCreateAdViewHolder(parent);
        } else {
            return mDelegateAdapter.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);

        if (viewType == VIEW_TYPE_AD) {
            onBindAdViewHolder(holder, position);
        } else {
            mDelegateAdapter.onBindViewHolder(holder, getPositionWithoutAds(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = mDelegateAdapter.getItemViewType(getPositionWithoutAds(position));

        if (mFrequency == 0) {
            return viewType;
        }

        if ((position + 1) % (mFrequency + 1) != 0) {
            return viewType;
        } else {
            return VIEW_TYPE_AD;
        }
    }

    private int getPositionWithoutAds(int position) {
        if (mFrequency == 0) {
            return position;
        }
        return position - (position + 1) / (mFrequency + 1);
    }

    @Override
    public int getItemCount() {
        int count = mDelegateAdapter.getItemCount();

        if (mFrequency == 0) {
            return count;
        }

        return count + count / mFrequency;
    }
}
