package ru.nbsp.pushka.service.api

import android.app.Service
import android.content.Intent
import android.os.IBinder
import ru.nbsp.pushka.BaseApplication
import ru.nbsp.pushka.annotation.ApiRepository
import ru.nbsp.pushka.bus.RxBus
import ru.nbsp.pushka.bus.event.LoadAlertsEvent
import ru.nbsp.pushka.bus.event.LoginEvent
import ru.nbsp.pushka.interactor.alert.StorageAlertInteractor
import ru.nbsp.pushka.interactor.user.UserInteractor
import ru.nbsp.pushka.network.auth.Account
import ru.nbsp.pushka.network.auth.AccountManager
import ru.nbsp.pushka.presentation.core.model.alert.PresentationAlert
import ru.nbsp.pushka.repository.alert.AlertsRepository
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
    lateinit var accountManager: AccountManager

    @Inject
    lateinit var timeStampUtils: TimestampUtils

    private val subscription = CompositeSubscription()

    companion object {
        const val ARG_SERVICE_COMMAND = "arg_service_command"
        const val ARG_LOGIN_PROVIDER = "arg_provider"
        const val ARG_LOGIN_TOKEN = "arg_token"
        const val COMMAND_LOGIN = "command_login"
        const val COMMAND_LOAD_ALERTS = "command_load_alerts"
        const val COMMAND_LOAD_SOURCES = "command_load_sources"
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
                handleLoadSourcesCommand(startId)
            }
        }

        return START_NOT_STICKY
    }

    private fun handleLoadAlertsCommand(startId: Int) {
        apiAlertsRepository.getAlerts()
                .flatMap {
                    storageAlertInteractor.saveAlerts(it)
                }
                .subscribe(LoadAlertsSubscriber(startId))
    }

    private fun handleLoadSourcesCommand(startId: Int) {
        // TODO: handle
//        apiSourcesRepository.getSources()
//                .flatMap {
//
//                }
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
}