package ru.nbsp.pushka.service.api

import android.app.Service
import android.content.Intent
import android.os.IBinder
import ru.nbsp.pushka.BaseApplication
import ru.nbsp.pushka.bus.RxBus
import ru.nbsp.pushka.bus.event.BaseEvent
import ru.nbsp.pushka.bus.event.auth.LoginEvent
import ru.nbsp.pushka.interactor.user.UserInteractor
import ru.nbsp.pushka.network.auth.Account
import ru.nbsp.pushka.network.auth.AccountManager
import ru.nbsp.pushka.service.BaseEventSubscriber
import ru.nbsp.pushka.util.TimestampUtils
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

/**
 * Created by Dimorinny on 12.02.16.
 */
class ApiAuthService : Service() {

    @Inject
    lateinit var bus: RxBus

    @Inject
    lateinit var userInteractor: UserInteractor

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
        }

        return START_NOT_STICKY
    }

    private fun handleLoginCommand(intent: Intent, startId: Int) {
        val token = intent.getStringExtra(ARG_LOGIN_TOKEN)
        val provider = intent.getStringExtra(ARG_LOGIN_PROVIDER)

        userInteractor.login(provider, token)
                .map {
                    val user = it.user
                    val identity = it.identity
                    Account(user.userId, user.firstName, user.lastName, user.photo,
                            identity.accessToken, identity.refreshToken,
                            timeStampUtils.currentTimestamp() + identity.expires)
                }
                .doOnNext {
                    accountManager.setAccount(it)
                }
                .subscribe(object : BaseEventSubscriber(this, startId, bus) {
                    override fun error(t: Throwable): BaseEvent {
                        return LoginEvent.Error(t)
                    }

                    override fun success(): BaseEvent {
                        return LoginEvent.Success()
                    }
                })
    }
}