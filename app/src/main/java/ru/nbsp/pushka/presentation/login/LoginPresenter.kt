package ru.nbsp.pushka.presentation.login

import ru.nbsp.pushka.R
import ru.nbsp.pushka.bus.RxBus
import ru.nbsp.pushka.bus.event.auth.LoginEvent
import ru.nbsp.pushka.di.annotation.UISched
import ru.nbsp.pushka.gcm.manage.GcmManager
import ru.nbsp.pushka.network.auth.AccountManager
import ru.nbsp.pushka.presentation.core.base.BasePresenter
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
class LoginPresenter
    @Inject constructor(
            @UISched val resultScheduler: Scheduler,
            val accountManager: AccountManager,
            val bus: RxBus,
            val gcmManager: GcmManager,
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

    fun onHelpButtonClicked() {
        view?.openHelp()
    }

    override fun onCreate() {
        super.onCreate()

        if(accountManager.isAccountValid()) {
            view?.openNavigationWindow()
            return
        }

        val loginSubscription = bus.events(LoginEvent::class.java)
                .flatMap {
                    when(it) {
                        is LoginEvent.Success -> Observable.just(true)
                        is LoginEvent.Error -> Observable.just(false)
                    }
                }
                .observeOn(resultScheduler)
                .subscribe(LoginSubscriber())

        subscription.add(loginSubscription)
    }

    fun onLoginSuccess(provider: String, token: String) {
        view?.showDialog()
        serviceManager.login(provider, token)
    }

    fun onLoginError() {
        view?.showAlert(stringUtils.getString(R.string.login_auth_error))
    }

    override fun onDestroy() {
        subscription.unsubscribe()
        super.onDestroy()
    }

    inner class LoginSubscriber : Subscriber<Boolean>() {
        override fun onCompleted() {}

        override fun onError(t: Throwable) {
            t.printStackTrace()
        }

        override fun onNext(success: Boolean) {
            view?.hideDialog()

            if (success) {
                gcmManager.clear()
                gcmManager.init()
                view?.openNavigationWindow()
            } else {
                onLoginError()
            }
        }
    }
}