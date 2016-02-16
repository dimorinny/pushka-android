package ru.nbsp.pushka

import android.app.Application
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
        initAppComponent()
    }

    fun initAppComponent() {
        graph = DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .build()
    }
}