package ru.nbsp.pushka

import android.app.Application
import android.os.StrictMode
import com.facebook.stetho.Stetho
import com.squareup.leakcanary.LeakCanary
import com.vk.sdk.VKSdk
import eu.inloop.easygcm.EasyGcm
import ru.nbsp.pushka.di.AppComponent
import ru.nbsp.pushka.di.AppModule
import ru.nbsp.pushka.di.DaggerAppComponent
import ru.nbsp.pushka.gcm.PushkaGcmListener

/**
 * Created by Dimorinny on 11.02.16.
 */
class BaseApplication : Application() {

    companion object {
        lateinit var graph: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        initAppComponent()

        LeakCanary.install(this)
        StrictMode.enableDefaults()
        initSocial()
        initStetho()
        EasyGcm.setGcmListener(PushkaGcmListener())
    }

    private fun initStetho() {
        Stetho.initializeWithDefaults(this);
    }

    private fun initSocial() {
        VKSdk.initialize(applicationContext)
    }

    fun initAppComponent() {
        graph = DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .build()
    }
}