package ru.nbsp.pushka.di

import dagger.Component
import ru.nbsp.pushka.BaseApplication
import ru.nbsp.pushka.data.DataModule
import ru.nbsp.pushka.gcm.GcmModule
import ru.nbsp.pushka.gcm.PushkaGcmListener
import ru.nbsp.pushka.interactor.InteractorModule
import ru.nbsp.pushka.network.ApiModule
import ru.nbsp.pushka.network.auth.social.SocialAuthManager
import ru.nbsp.pushka.presentation.alert.detail.AlertActivity
import ru.nbsp.pushka.presentation.alert.feed.AlertsFragment
import ru.nbsp.pushka.presentation.category.feed.CategoriesFragment
import ru.nbsp.pushka.presentation.core.base.BaseActivity
import ru.nbsp.pushka.presentation.core.base.OneFragmentActivity
import ru.nbsp.pushka.presentation.login.LoginActivity
import ru.nbsp.pushka.presentation.navigation.NavigationActivity
import ru.nbsp.pushka.presentation.settings.SettingsFragment
import ru.nbsp.pushka.presentation.source.feed.SourcesActivity
import ru.nbsp.pushka.presentation.source.feed.SourcesFragment
import ru.nbsp.pushka.presentation.subscription.feed.SubscriptionsFragment
import ru.nbsp.pushka.presentation.subscription.params.ParamsFragment
import ru.nbsp.pushka.presentation.subscription.params.control.SimpleListFragment
import ru.nbsp.pushka.presentation.subscription.subscribe.SubscribeActivity
import ru.nbsp.pushka.repository.RepositoryModule
import ru.nbsp.pushka.service.api.ApiPushkaService
import javax.inject.Singleton

/**
 * Created by Dimorinny on 11.02.16.
 */
@Singleton
@Component(modules = arrayOf(
        ApiModule::class,
        AppModule::class,
        SchedulerModule::class,
        RepositoryModule::class,
        InteractorModule::class,
        DataModule::class,
        GcmModule::class
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
    fun inject(sourcesFragment: SourcesFragment)
    fun inject(sourcesActivity: SourcesActivity)
    fun inject(subscriptionsFragment: SubscriptionsFragment)
    fun inject(categoriesFragment: CategoriesFragment)
    fun inject(paramsFragment: ParamsFragment)
    fun inject(subscribeActivity: SubscribeActivity)
    fun inject(alertActivity: AlertActivity)
    fun inject(simpleListFragment: SimpleListFragment)
    fun inject(pushkaGcmListener: PushkaGcmListener)
}