package ru.nbsp.pushka.presentation.alert.feed

import ru.nbsp.pushka.annotation.StorageRepository
import ru.nbsp.pushka.bus.RxBus
import ru.nbsp.pushka.bus.event.LoadAlertsEvent
import ru.nbsp.pushka.presentation.core.base.BasePresenter
import ru.nbsp.pushka.presentation.core.model.alert.PresentationAlert
import ru.nbsp.pushka.presentation.core.state.State
import ru.nbsp.pushka.repository.alert.AlertsRepository
import ru.nbsp.pushka.service.ServiceManager
import rx.Observable
import rx.Subscriber
import rx.subscriptions.CompositeSubscription
import java.util.*
import javax.inject.Inject

/**
 * Created by Dimorinny on 24.02.16.
 */
class AlertsPresenter
        @Inject constructor(@StorageRepository val storageAlertsRepository: AlertsRepository,
                            val rxBus: RxBus,
                            val serviceManager: ServiceManager) : BasePresenter {

    override var view: AlertsView? = null

    val subscription: CompositeSubscription = CompositeSubscription()
    var alerts: List<PresentationAlert> = ArrayList()

    override fun onCreate() {
        super.onCreate()

        subscription.add(rxBus.events(LoadAlertsEvent::class.java)
                .flatMap {
                    when (it) {
                        is LoadAlertsEvent.Success -> storageAlertsRepository.getAlerts()
                        is LoadAlertsEvent.Error -> Observable.error(it.t)
                    }
                }.subscribe(LoadAlertsNetworkSubscriber()))
    }

    fun loadAlertsFromCache() {
        subscription.add(storageAlertsRepository.getAlerts().subscribe(LoadAlertsCacheSubscriber()))
    }

    fun loadAlertsFromServer() {
        view?.setState(State.STATE_PROGRESS)
        view?.setToolbarState(State.STATE_PROGRESS)
        serviceManager.loadAlerts()
    }

    fun onAlertClicked(index: Int) {
        view?.openUrl(alerts[index].shareLink)
    }

    inner class LoadAlertsCacheSubscriber : Subscriber<List<PresentationAlert>>() {
        override fun onCompleted() {}

        override fun onError(t: Throwable) {
            t.printStackTrace()
        }

        override fun onNext(result: List<PresentationAlert>) {
            alerts = result

            if (!result.isEmpty()) {
                view?.setState(State.STATE_NORMAL)
            }

            view?.setAlerts(result)
        }
    }

    inner class LoadAlertsNetworkSubscriber : Subscriber<List<PresentationAlert>>() {
        override fun onCompleted() {}

        override fun onError(t: Throwable) {
            t.printStackTrace()

            if (alerts.size == 0) {
                view?.setState(State.STATE_ERROR)
                view?.setToolbarState(State.STATE_NORMAL)
            } else {
                view?.setToolbarState(State.STATE_ERROR)
            }
        }

        override fun onNext(result: List<PresentationAlert>) {
            alerts = result
            view?.setToolbarState(State.STATE_NORMAL)
            view?.setState(if (result.isEmpty()) State.STATE_EMPTY else State.STATE_NORMAL)
            view?.setAlerts(result)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        subscription.unsubscribe()
    }
}