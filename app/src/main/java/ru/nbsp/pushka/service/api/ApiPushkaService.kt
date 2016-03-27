package ru.nbsp.pushka.service.api

import android.app.Service
import android.content.Intent
import android.os.IBinder
import ru.nbsp.pushka.BaseApplication
import ru.nbsp.pushka.annotation.ApiRepository
import ru.nbsp.pushka.bus.RxBus
import ru.nbsp.pushka.bus.event.*
import ru.nbsp.pushka.interactor.alert.StorageAlertInteractor
import ru.nbsp.pushka.interactor.category.StorageCategoryInteractor
import ru.nbsp.pushka.interactor.source.StorageSourceInteractor
import ru.nbsp.pushka.interactor.user.UserInteractor
import ru.nbsp.pushka.network.auth.Account
import ru.nbsp.pushka.network.auth.AccountManager
import ru.nbsp.pushka.presentation.core.model.alert.PresentationAlert
import ru.nbsp.pushka.presentation.core.model.source.PresentationCategory
import ru.nbsp.pushka.presentation.core.model.source.PresentationSource
import ru.nbsp.pushka.repository.alert.AlertsRepository
import ru.nbsp.pushka.repository.category.CategoriesRepository
import ru.nbsp.pushka.repository.source.SourcesRepository
import ru.nbsp.pushka.util.TimestampUtils
import rx.Subscriber
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

/**
 * Created by Dimorinny on 12.02.16.
 */
class ApiPushkaService : Service() {

    @Inject
    lateinit var bus: RxBus

    @Inject
    lateinit var userInteractor: UserInteractor

    @field:[Inject ApiRepository]
    lateinit var apiAlertsRepository: AlertsRepository

    @Inject
    lateinit var storageAlertInteractor: StorageAlertInteractor

    @field:[Inject ApiRepository]
    lateinit var apiSourcesRepository: SourcesRepository

    @Inject
    lateinit var storageSourcesInteractor: StorageSourceInteractor

    @field:[Inject ApiRepository]
    lateinit var apiCategoriesRepository: CategoriesRepository

    @Inject
    lateinit var storageCategoryInteractor: StorageCategoryInteractor

    @Inject
    lateinit var accountManager: AccountManager

    @Inject
    lateinit var timeStampUtils: TimestampUtils

    private val subscription = CompositeSubscription()

    companion object {
        const val ARG_SERVICE_COMMAND = "arg_service_command"
        const val ARG_LOGIN_PROVIDER = "arg_provider"
        const val ARG_LOGIN_TOKEN = "arg_token"
        const val ARG_CATEGORY_ID = "arg_category_id"
        const val ARG_ALERT_ID = "arg_alert_id"
        const val COMMAND_LOGIN = "command_login"
        const val COMMAND_LOAD_ALERTS = "command_load_alerts"
        const val COMMAND_LOAD_ALERT = "command_load_alert"
        const val COMMAND_LOAD_SOURCES = "command_load_sources"
        const val COMMAND_LOAD_CATEGORIES = "command_load_categories"
    }

    override fun onBind(intent: Intent?): IBinder? { return null }

    override fun onCreate() {
        super.onCreate()
        BaseApplication.graph.inject(this)
    }

    override fun onDestroy() {
        subscription.unsubscribe()
        super.onDestroy()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent == null) {
            return START_NOT_STICKY
        }

        val command = intent.getStringExtra(ARG_SERVICE_COMMAND)

        when (command) {
            COMMAND_LOGIN -> {
                handleLoginCommand(intent, startId)
            }
            COMMAND_LOAD_ALERTS -> {
                handleLoadAlertsCommand(startId)
            }
            COMMAND_LOAD_SOURCES -> {
                handleLoadSourcesCommand(intent, startId)
            }
            COMMAND_LOAD_CATEGORIES -> {
                handleLoadCategoriesCommand(startId)
            }
            COMMAND_LOAD_ALERT -> {
                handleLoadAlertCommand(intent, startId)
            }
        }

        return START_NOT_STICKY
    }

    private fun handleLoadAlertCommand(intent: Intent, startId: Int) {
        val alertId = intent.getStringExtra(ARG_ALERT_ID)

        apiAlertsRepository.getAlert(alertId)
                .flatMap {
                    storageAlertInteractor.saveAlert(it)
                }
                .subscribe(LoadAlertSubscriber(startId))
    }

    private fun handleLoadAlertsCommand(startId: Int) {
        apiAlertsRepository.getAlerts()
                .flatMap {
                    storageAlertInteractor.saveAlerts(it)
                }
                .subscribe(LoadAlertsSubscriber(startId))
    }

    private fun handleLoadCategoriesCommand(startId: Int) {
        apiCategoriesRepository.getCategories()
                .flatMap {
                    storageCategoryInteractor.saveCategories(it)
                }
                .subscribe(LoadCategoriesSubscriber(startId))
    }

    private fun handleLoadSourcesCommand(intent: Intent, startId: Int) {
        val categoryId = intent.getStringExtra(ARG_CATEGORY_ID)

        apiSourcesRepository.getSources(categoryId)
                .flatMap {
                    storageSourcesInteractor.saveSources(it, categoryId)
                }
                .subscribe(LoadSourcesSubscriber(startId))
    }

    private fun handleLoginCommand(intent: Intent, startId: Int) {
        val token = intent.getStringExtra(ARG_LOGIN_TOKEN)
        val provider = intent.getStringExtra(ARG_LOGIN_PROVIDER)

        userInteractor.login(provider, token)
                .map {
                    val user = it.user
                    val identity = it.identity
                    Account(user.firstName, user.lastName, user.photo,
                            identity.accessToken, identity.refreshToken,
                            timeStampUtils.currentTimestamp() + identity.expires)
                }
                .doOnNext {
                    accountManager.setAccount(it)
                }
                .subscribe(LoginSubscriber(startId))
    }

    inner class LoginSubscriber(val startId: Int) : Subscriber<Account>() {
        override fun onCompleted() {}

        override fun onError(t: Throwable) {
            bus.post(LoginEvent.Error(t) as LoginEvent)
            stopSelf(startId)
        }

        override fun onNext(account: Account) {
            bus.post(LoginEvent.Success() as LoginEvent)
            stopSelf(startId)
        }
    }

    inner class LoadAlertsSubscriber(val startId: Int) : Subscriber<List<PresentationAlert>>() {
        override fun onCompleted() {}

        override fun onError(t: Throwable) {
            bus.post(LoadAlertsEvent.Error(t) as LoadAlertsEvent)
            stopSelf(startId)
        }

        override fun onNext(alerts: List<PresentationAlert>) {
            bus.post(LoadAlertsEvent.Success() as LoadAlertsEvent)
            stopSelf(startId)
        }
    }

    inner class LoadAlertSubscriber(val startId: Int) : Subscriber<PresentationAlert>() {
        override fun onCompleted() {}

        override fun onError(t: Throwable) {
            bus.post(LoadAlertEvent.Error(t) as LoadAlertEvent)
            stopSelf(startId)
        }

        override fun onNext(alert: PresentationAlert) {
            bus.post(LoadAlertEvent.Success(alert.id) as LoadAlertEvent)
            stopSelf(startId)
        }
    }

    inner class LoadCategoriesSubscriber(val startId: Int) : Subscriber<List<PresentationCategory>>() {
        override fun onCompleted() {}

        override fun onError(t: Throwable) {
            bus.post(LoadCategoriesEvent.Error(t) as LoadCategoriesEvent)
            stopSelf(startId)
        }

        override fun onNext(alerts: List<PresentationCategory>) {
            bus.post(LoadCategoriesEvent.Success() as LoadCategoriesEvent)
            stopSelf(startId)
        }
    }

    inner class LoadSourcesSubscriber(val startId: Int) : Subscriber<List<PresentationSource>>() {
        override fun onCompleted() {}

        override fun onError(t: Throwable) {
            bus.post(LoadSourcesEvent.Error(t) as LoadSourcesEvent)
            stopSelf(startId)
        }

        override fun onNext(alerts: List<PresentationSource>) {
            bus.post(LoadSourcesEvent.Success() as LoadSourcesEvent)
            stopSelf(startId)
        }
    }
}