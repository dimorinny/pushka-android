package ru.nbsp.pushka.presentation.settings

import ru.nbsp.pushka.bus.RxBus
import ru.nbsp.pushka.bus.event.device.RemoveGcmDeviceEvent
import ru.nbsp.pushka.interactor.app.ApplicationInteractor
import ru.nbsp.pushka.presentation.core.base.BasePresenter
import ru.nbsp.pushka.service.ServiceManager
import rx.Observable
import rx.Subscriber
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

/**
 * Created by Dimorinny on 23.05.16.
 */
class SettingsPresenter
    @Inject constructor(
            val rxBus: RxBus,
            val serviceManager: ServiceManager,
            val applicationInteractor: ApplicationInteractor) : BasePresenter {

    override var view: SettingsView? = null
    val subscription: CompositeSubscription = CompositeSubscription()

    override fun onCreate() {
        super.onCreate()
        observeRemoveGcmDeviceEvent()
    }

    private fun observeRemoveGcmDeviceEvent() {
        subscription.add(rxBus.events(RemoveGcmDeviceEvent::class.java)
                .flatMap {
                    when (it) {
                        is RemoveGcmDeviceEvent.Success -> Observable.just(Any())
                        is RemoveGcmDeviceEvent.Error -> Observable.error(it.t)
                    }
                }
                .doOnNext { applicationInteractor.logout() }
                .subscribe(ClearDeviceSubscriber()))

    }

    fun logoutItemClicked() {
        view?.showLogoutDialog()
    }

    fun logoutDialogPositiveClicked() {
        view?.hideLogoutDialog()
        view?.showLogoutProgressDialog()
        serviceManager.removeGcmDevice()
    }

    inner class ClearDeviceSubscriber : Subscriber<Any>() {
        override fun onCompleted() {}

        override fun onError(t: Throwable) {
            t.printStackTrace()
            view?.hideLogoutProgressDialog()

            observeRemoveGcmDeviceEvent()
        }

        override fun onNext(t: Any) {
            view?.hideLogoutProgressDialog()
            view?.openLoginScreen()
        }
    }

    override fun onDestroy() {
        subscription.unsubscribe()
        super.onDestroy()
    }
}