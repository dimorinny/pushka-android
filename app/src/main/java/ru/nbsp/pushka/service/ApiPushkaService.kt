package ru.nbsp.pushka.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import ru.nbsp.pushka.BaseApplication
import ru.nbsp.pushka.api.response.LoginResponse
import ru.nbsp.pushka.bus.RxBus
import ru.nbsp.pushka.bus.event.LoginEvent
import ru.nbsp.pushka.iteractor.login.LoginIteractor
import ru.nbsp.pushka.iteractor.login.param.LoginParam
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
    lateinit var loginIteractor: LoginIteractor

    private val subscription = CompositeSubscription()

    companion object {
        const val ARG_SERVICE_COMMAND = "arg_service_command"
        const val ARG_LOGIN_PROVIDER = "arg_provider"
        const val ARG_LOGIN_TOKEN = "arg_token"
        const val COMMAND_LOGIN = "command_login"
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

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

        subscription.add(loginIteractor.execute(LoginParam(provider, token), LoginSubscriber(startId)))
    }

    inner class LoginSubscriber(val startId: Int) : Subscriber<LoginResponse>() {
        override fun onCompleted() {}

        override fun onError(t: Throwable) {
            bus.post(LoginEvent.Error(t) as LoginEvent)
            stopSelf(startId)
        }

        override fun onNext(response: LoginResponse) {
            bus.post(LoginEvent.Response(response) as LoginEvent)
            stopSelf(startId)
        }
    }
}