package ru.nbsp.pushka.presentation.core.ad;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.NativeExpressAdView;

import ru.nbsp.pushka.R;

/**
 * Created by Dimorinny on 12.06.16.
 */
public class AdmobNativeAdapter<T extends RecyclerView.Adapter<RecyclerView.ViewHolder>>
        extends BaseAdAdapter<T> {

    private BaseAdmobFetcher mAdmobFetcher;

    public AdmobNativeAdapter(T adapter, BaseAdmobFetcher fetcher) {
        super(adapter);
        mAdmobFetcher = fetcher;
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
        mAdmobFetcher.setupAd(adHolder.mAdView);
        mAdmobFetcher.fetchAd(adHolder.mAdView);
    }

    public void onDestroy() {
        mAdmobFetcher.destroyAllAds();
    }
}

