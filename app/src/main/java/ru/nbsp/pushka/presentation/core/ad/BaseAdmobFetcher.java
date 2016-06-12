package ru.nbsp.pushka.presentation.core.ad;

import android.content.Context;
import android.text.TextUtils;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.NativeExpressAdView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdmobFetcher {

    protected List<AdmobListener> mAdNativeListeners = new ArrayList<AdmobListener>();
    protected int mNoOfFetchedAds;
    protected int mFetchFailCount;
    protected WeakReference<Context> mContext = new WeakReference<Context>(null);
    protected String admobReleaseUnitId;

    /**
     * Fetches a new native ad.
     */
    abstract protected void fetchAd(final NativeExpressAdView adView);

    /**
     * Subscribing to the native ads events
     * @param adView
     */
    abstract protected void setupAd(final NativeExpressAdView adView);

    public String getAdmobReleaseUnitId() {
        return admobReleaseUnitId;
    }

    /*
    *Sets a release unit ID for admob banners. ID should be active, please check it in your Admob's account.
    * Be careful: don't set it or set to null if you still haven't deployed a Release.
    * Otherwise your Admob account could be banned
    */
    public void setAdmobReleaseUnitId(String admobReleaseUnitId) {
        this.admobReleaseUnitId = admobReleaseUnitId;
    }

    protected String testDeviceId;

    public String getTestDeviceId() {
        return testDeviceId;
    }

    /*
    *Sets a test device ID. Normally you don't have to set it
     */
    public void setTestDeviceId(String testDeviceId) {
        this.testDeviceId = testDeviceId;
    }

    /**
     * Adds an {@link AdmobListener} that would be notified for any changes to the native ads
     * list.
     *
     * @param listener the listener to be notified
     */
    public synchronized void addListener(AdmobListener listener) {
        mAdNativeListeners.add(listener);
    }

    /**
     * Gets the number of ads that have been fetched so far.
     *
     * @return the number of ads that have been fetched
     */
    public synchronized int getFetchedAdsCount() {
        return mNoOfFetchedAds;
    }

    /**
     * Fetches a new native ad.
     *
     * @param context the current context.
     * @see #destroyAllAds()
     */
    public synchronized void prefetchAds(Context context) {
        mContext = new WeakReference<>(context);
    }

    /**
     * Destroys ads that have been fetched, that are still being fetched and removes all resource
     * references that this instance still has. This should only be called when the Activity that
     * is showing ads is closing, preferably from the {@link android.app.Activity#onDestroy()}.
     * </p>
     * The converse of this call is {@link #prefetchAds(Context)}.
     */
    public synchronized void destroyAllAds() {
        mFetchFailCount = 0;
        mNoOfFetchedAds = 0;
        mContext.clear();
        notifyObserversOfAdSizeChange();
    }

    /**
     * Notifies all registered {@link AdmobListener} of a change in ad data count.
     */
    protected void notifyObserversOfAdSizeChange() {
        for (AdmobListener listener : mAdNativeListeners) {
            listener.onAdCountChanged();
        }
    }

    /**
     * Setup and get an ads request
     */
    protected synchronized AdRequest getAdRequest() {
        AdRequest.Builder adBldr = new AdRequest.Builder();
        if(!TextUtils.isEmpty(getTestDeviceId()))
            adBldr.addTestDevice(getTestDeviceId());
        return adBldr.build();
    }

    /**
     * Listener that is notified when changes to the list of fetched native ads are made.
     */
    public interface AdmobListener {

        /**
         * Raised when the number of ads have changed. Adapters that implement this class
         * should notify their data views that the dataset has changed.
         */
        void onAdCountChanged();
    }
}