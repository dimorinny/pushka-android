package ru.nbsp.pushka

import android.os.StrictMode
import android.support.multidex.MultiDexApplication
import com.facebook.stetho.Stetho
import com.squareup.leakcanary.LeakCanary
import com.vk.sdk.VKSdk
import com.yandex.metrica.YandexMetrica
import eu.inloop.easygcm.EasyGcm
import ru.nbsp.pushka.di.AppComponent
import ru.nbsp.pushka.di.AppModule
import ru.nbsp.pushka.di.DaggerAppComponent
import ru.nbsp.pushka.gcm.PushkaGcmListener

/**
 * Created by Dimorinny on 11.02.16.
 */
class BaseApplication : MultiDexApplication() {

    companion object {
        lateinit var graph: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        initAppComponent()

        if (BuildConfig.DEBUG) {
            LeakCanary.install(this)
            initStetho()
            StrictMode.enableDefaults()
        } else {
            initYandexMetrica()
        }

        initSocial()
        EasyGcm.setGcmListener(PushkaGcmListener())
    }

    private fun initStetho() {
        Stetho.initializeWithDefaults(this);
    }

    private fun initSocial() {
        VKSdk.initialize(applicationContext)
    }

    private fun initYandexMetrica() {
        YandexMetrica.activate(this, resources.getString(R.string.yandex_metrica_api_key))
        YandexMetrica.enableActivityAutoTracking(this)
    }

    fun initAppComponent() {
        graph = DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .build()
    }
}