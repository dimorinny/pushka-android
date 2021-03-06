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
import ru.nbsp.pushka.presentation.core.base.OneFragmentNavigationActivity
import ru.nbsp.pushka.presentation.device.feed.DevicesFragment
import ru.nbsp.pushka.presentation.device.feed.container.DevicesActivity
import ru.nbsp.pushka.presentation.login.LoginActivity
import ru.nbsp.pushka.presentation.navigation.NavigationActivity
import ru.nbsp.pushka.presentation.settings.SettingsFragment
import ru.nbsp.pushka.presentation.source.feed.SourcesActivity
import ru.nbsp.pushka.presentation.source.feed.SourcesFragment
import ru.nbsp.pushka.presentation.subscription.edit.EditSubscriptionActivity
import ru.nbsp.pushka.presentation.subscription.feed.SubscriptionsFragment
import ru.nbsp.pushka.presentation.subscription.params.ParamsFragment
import ru.nbsp.pushka.presentation.subscription.params.control.autocomplete.AutoCompleteControl
import ru.nbsp.pushka.presentation.subscription.subscribe.SubscribeActivity
import ru.nbsp.pushka.repository.RepositoryModule
import ru.nbsp.pushka.service.api.*
import ru.nbsp.pushka.service.application.DataService
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

    // Services
    fun inject(alertService: ApiAlertService)
    fun inject(sourceService: ApiSourceService)
    fun inject(authService: ApiAuthService)
    fun inject(subscriptionService: ApiSubscriptionService)
    fun inject(deviceService: ApiDeviceService)
    fun inject(dataService: DataService)

    // Interactors

    // Ui
    fun inject(baseActivity: BaseActivity)
    fun inject(loginActivity: LoginActivity)
    fun inject(navigationActivity: NavigationActivity)
    fun inject(oneFragmentActivity: OneFragmentActivity)
    fun inject(oneFragmentNavigationActivity: OneFragmentNavigationActivity)
    fun inject(devicesActivity: DevicesActivity)
    fun inject(settingsFragment: SettingsFragment)
    fun inject(alertsFragment: AlertsFragment)
    fun inject(devicesFragment: DevicesFragment)
    fun inject(sourcesFragment: SourcesFragment)
    fun inject(sourcesActivity: SourcesActivity)
    fun inject(subscriptionsFragment: SubscriptionsFragment)
    fun inject(categoriesFragment: CategoriesFragment)
    fun inject(paramsFragment: ParamsFragment)
    fun inject(subscribeActivity: SubscribeActivity)
    fun inject(editSubscriptionActivity: EditSubscriptionActivity)
    fun inject(alertActivity: AlertActivity)
    fun inject(autoCompleteControl: AutoCompleteControl)

    // Auth
    fun inject(socialAuthManager: SocialAuthManager)

    // Gcm
    fun inject(pushkaGcmListener: PushkaGcmListener)
}