package ru.nbsp.pushka.di

import dagger.Component
import ru.nbsp.pushka.BaseApplication
import ru.nbsp.pushka.api.ApiModule
import ru.nbsp.pushka.service.ApiPushkaService
import ru.nbsp.pushka.service.ServiceModule

/**
 * Created by Dimorinny on 11.02.16.
 */

@Component(modules = arrayOf(
        AppModule::class,
        ApiModule::class,
        ServiceModule::class
))
interface AppComponent {
    fun inject(application: BaseApplication)
    fun inject(pushkaService: ApiPushkaService)
}