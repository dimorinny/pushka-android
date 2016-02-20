package ru.nbsp.pushka.mvp.presenters.login

import android.util.Log
import ru.nbsp.pushka.R
import ru.nbsp.pushka.annotation.IOSched
import ru.nbsp.pushka.bus.RxBus
import ru.nbsp.pushka.bus.event.LoginEvent
import ru.nbsp.pushka.mvp.presenters.BasePresenter
import ru.nbsp.pushka.mvp.views.login.LoginView
import ru.nbsp.pushka.service.ServiceManager
import ru.nbsp.pushka.util.StringUtils
import rx.Observable
import rx.Scheduler
import rx.Subscriber
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

/**
 * Created by Dimorinny on 16.02.16.
 */

class LoginPresenter @Inject constructor(
        @IOSched val resultScheduler: Scheduler,
        val bus: RxBus,
        val serviceManager: ServiceManager,
        val stringUtils: StringUtils): BasePresenter {

    override var view: LoginView? = null
    private val subscription = CompositeSubscription()

    fun onGoogleLoginButtonClicked() {
        view?.openGoogleLoginDialog()
    }

    fun onVkLoginButtonClicked() {
        view?.openVkLoginDialog()
    }

    override fun onCreate() {
        super.onCreate()

        val loginSubscription = bus.events(LoginEvent::class.java)
                .flatMap {
                    when(it) {
                        is LoginEvent.Success -> Observable.just(true)
                        is LoginEvent.Error -> Observable.error(it.t)
                    }
                }
                .observeOn(resultScheduler)
                .subscribe(LoginSubscriber())

        subscription.add(loginSubscription)
    }

    fun onLoginSuccess(provider: String, token: String) {
        serviceManager.login(provider, token)
    }

    fun onLoginError() {
        view?.showAlert(stringUtils.getString(R.string.login_auth_error))
    }

    override fun onDestroy() {
        subscription.unsubscribe()
        super.onDestroy()
    }

    class LoginSubscriber : Subscriber<Boolean>() {
        override fun onCompleted() {}

        override fun onError(t: Throwable) {
            t.printStackTrace()
        }

        override fun onNext(success: Boolean) {
            Log.v("LoginPresenter", success.toString())
        }
    }
}