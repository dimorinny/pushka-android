package ru.nbsp.pushka

import android.app.Application
import android.os.StrictMode
import com.squareup.leakcanary.LeakCanary
import com.vk.sdk.VKSdk
import ru.nbsp.pushka.di.AppComponent
import ru.nbsp.pushka.di.AppModule
import ru.nbsp.pushka.di.DaggerAppComponent

/**
 * Created by Dimorinny on 11.02.16.
 */
class BaseApplication : Application() {

    companion object {
        lateinit var graph: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        LeakCanary.install(this)
        StrictMode.enableDefaults()

        initSocial()
        initAppComponent()
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