package ru.nbsp.pushka.presentation.alert.feed

import ru.nbsp.pushka.annotation.ApiRepository
import ru.nbsp.pushka.annotation.StorageRepository
import ru.nbsp.pushka.bus.RxBus
import ru.nbsp.pushka.bus.event.LoadAlertsEvent
import ru.nbsp.pushka.data.entity.Alert
import ru.nbsp.pushka.presentation.core.base.BasePresenter
import ru.nbsp.pushka.repository.alert.AlertsRepository
import ru.nbsp.pushka.service.ServiceManager
import rx.Observable
import rx.Subscriber
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

/**
 * Created by Dimorinny on 24.02.16.
 */
class AlertsPresenter
        @Inject constructor(@ApiRepository val apiAlertsRepository: AlertsRepository,
                            @StorageRepository val storageAlertsRepository: AlertsRepository,
                            val rxBus: RxBus,
                            val serviceManager: ServiceManager) : BasePresenter {

    override var view: AlertsView? = null

    val subscription: CompositeSubscription = CompositeSubscription()
    lateinit var alerts: List<Alert>

    override fun onCreate() {
        super.onCreate()

        subscription.add(rxBus.events(LoadAlertsEvent::class.java)
                .flatMap {
                    when (it) {
                        is LoadAlertsEvent.Success -> storageAlertsRepository.getAlerts()
                        is LoadAlertsEvent.Error -> Observable.error(it.t)
                    }
                }.subscribe(LoadAlertsSubscriber()))
    }

    fun loadAlertsFromCache() {
        subscription.add(storageAlertsRepository.getAlerts().subscribe(LoadAlertsSubscriber()))
    }

    fun loadAlertsFromServer() {
        serviceManager.loadAlerts()
    }

    fun onAlertClicked(index: Int) {
        view?.openUrl(alerts[index].shareLink)
    }

    inner class LoadAlertsSubscriber : Subscriber<List<Alert>>() {
        override fun onCompleted() {}

        override fun onError(t: Throwable) {
            t.printStackTrace()

            if (alerts.size == 0) {
                view?.setErrorState()
            }
        }

        override fun onNext(result: List<Alert>) {
            alerts = result
            view?.setAlerts(result)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        subscription.unsubscribe()
    }
}