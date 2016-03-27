package ru.nbsp.pushka.presentation.alert.detail

import ru.nbsp.pushka.annotation.StorageRepository
import ru.nbsp.pushka.bus.RxBus
import ru.nbsp.pushka.bus.event.LoadAlertEvent
import ru.nbsp.pushka.presentation.core.base.BasePresenter
import ru.nbsp.pushka.presentation.core.model.alert.PresentationAlert
import ru.nbsp.pushka.repository.alert.AlertsRepository
import ru.nbsp.pushka.service.ServiceManager
import rx.Observable
import rx.Subscriber
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

/**
 * Created by Dimorinny on 16.03.16.
 */
class AlertPresenter
    @Inject constructor(@StorageRepository val storageAlertsRepository: AlertsRepository,
                        val rxBus: RxBus,
                        val serviceManager: ServiceManager) : BasePresenter {

    override var view: AlertView? = null
    var alert: PresentationAlert? = null
    val subscription: CompositeSubscription = CompositeSubscription()

    override fun onCreate() {
        super.onCreate()

        subscription.add(rxBus.events(LoadAlertEvent::class.java)
                .flatMap {
                    when (it) {
                        is LoadAlertEvent.Success -> storageAlertsRepository.getAlert(it.alertId)
                        is LoadAlertEvent.Error -> Observable.error(it.t)
                    }
                }.subscribe(LoadAlertSubscriber()))
    }

    fun loadAlertFromCache(alertId: String) {
        subscription.add(storageAlertsRepository.getAlert(alertId).subscribe(LoadAlertSubscriber()))
    }

    fun loadAlertFromServer(alertId: String) {
        serviceManager.loadAlert(alertId)
    }

    fun onActionClicked(index: Int) {
        val action = alert!!.actions[index]

        when(action.type) {
            "url" -> handleUrlAction(action.value)
        }
    }

    private fun handleUrlAction(value: String) {
        // TODO: handle url action
    }

    inner class LoadAlertSubscriber : Subscriber<PresentationAlert>() {
        override fun onCompleted() {}

        override fun onError(t: Throwable) {
            t.printStackTrace()
        }

        override fun onNext(result: PresentationAlert) {
            if (result != alert) {
                view?.setContentUrl(result.actions[0].value)
                view?.setTitle(result.title)
                view?.setActions(result.actions)
            }

            alert = result
        }
    }

    override fun onDestroy() {
        subscription.unsubscribe()
        super.onDestroy()
    }
}