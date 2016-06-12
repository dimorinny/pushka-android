package ru.nbsp.pushka.presentation.alert.feed.adapter.ad;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.NativeExpressAdView;

import ru.nbsp.pushka.R;

/**
 * Created by Dimorinny on 12.06.16.
 */
public class AdMobNativeAdapter<T extends RecyclerView.Adapter<RecyclerView.ViewHolder>>
        extends BaseAdAdapter<T> {

    private AdRequest mAdRequest;

    public AdMobNativeAdapter(T adapter, AdRequest request) {
        super(adapter);
        mAdRequest = request;
    }

    class AdViewHolder extends RecyclerView.ViewHolder {
        private NativeExpressAdView mAdView;

        public AdViewHolder(final View view) {
            super(view);
            initViews(view);
        }

        private void initViews(View view) {
            mAdView = (NativeExpressAdView) view.findViewById(R.id.alert_ad);
        }
    }

    @Override
    RecyclerView.ViewHolder onCreateAdViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_alert_ad, parent, false);

        return new AdViewHolder(view);
    }

    @SuppressWarnings("unchecked")
    @Override
    void onBindAdViewHolder(RecyclerView.ViewHolder holder, int position) {
        AdViewHolder adHolder = (AdViewHolder) holder;
        adHolder.mAdView.loadAd(mAdRequest);
    }
}

