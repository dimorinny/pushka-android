package ru.nbsp.pushka.di

import dagger.Component
import ru.nbsp.pushka.BaseApplication
import ru.nbsp.pushka.api.ApiModule
import ru.nbsp.pushka.service.ApiPushkaService
import ru.nbsp.pushka.service.ServiceModule
import ru.nbsp.pushka.ui.login.LoginActivity
import javax.inject.Singleton

/**
 * Created by Dimorinny on 11.02.16.
 */

@Singleton
@Component(modules = arrayOf(
        AppModule::class,
        ApiModule::class,
        ServiceModule::class,
        SchedulerModule::class
))
interface AppComponent {
    fun inject(application: BaseApplication)
    fun inject(pushkaService: ApiPushkaService)
    fun inject(loginActivity: LoginActivity)
}