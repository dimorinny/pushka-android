package ru.nbsp.pushka.di

import dagger.Component
import ru.nbsp.pushka.BaseApplication
import ru.nbsp.pushka.api.ApiModule
import ru.nbsp.pushka.auth.social.SocialAuthManager
import ru.nbsp.pushka.iteractor.IteractorModule
import ru.nbsp.pushka.presentation.alert.feed.AlertsFragment
import ru.nbsp.pushka.presentation.core.base.BaseActivity
import ru.nbsp.pushka.presentation.core.base.OneFragmentActivity
import ru.nbsp.pushka.presentation.login.LoginActivity
import ru.nbsp.pushka.presentation.navigation.NavigationActivity
import ru.nbsp.pushka.presentation.settings.SettingsFragment
import ru.nbsp.pushka.repository.RepositoryModule
import ru.nbsp.pushka.service.api.ApiPushkaService
import javax.inject.Singleton

/**
 * Created by Dimorinny on 11.02.16.
 */
@Singleton
@Component(modules = arrayOf(
        AppModule::class,
        ApiModule::class,
        SchedulerModule::class,
        RepositoryModule::class,
        IteractorModule::class
))
interface AppComponent {
    fun inject(application: BaseApplication)
    fun inject(pushkaService: ApiPushkaService)
    fun inject(baseActivity: BaseActivity)
    fun inject(loginActivity: LoginActivity)
    fun inject(socialAuthManager: SocialAuthManager)
    fun inject(navigationActivity: NavigationActivity)
    fun inject(oneFragmentActivity: OneFragmentActivity)
    fun inject(settingsFragment: SettingsFragment)
    fun inject(alertsFragment: AlertsFragment)
}