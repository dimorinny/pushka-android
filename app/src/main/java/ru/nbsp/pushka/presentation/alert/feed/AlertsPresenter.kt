package ru.nbsp.pushka.presentation.alert.feed

import ru.nbsp.pushka.network.model.alert.Alert
import ru.nbsp.pushka.presentation.core.base.BasePresenter
import ru.nbsp.pushka.repository.alert.AlertsRepository
import rx.Subscriber
import javax.inject.Inject

/**
 * Created by Dimorinny on 24.02.16.
 */
class AlertsPresenter
        @Inject constructor(val alertsRepository: AlertsRepository) : BasePresenter {

    override var view: AlertsView? = null

    fun loadAlertsFromCache() {
        alertsRepository.getAlerts().subscribe(LoadAlertsSubscriber())
    }

    inner class LoadAlertsSubscriber : Subscriber<List<Alert>>() {
        override fun onCompleted() {}

        override fun onError(t: Throwable) {
            t.printStackTrace()
        }

        override fun onNext(alerts: List<Alert>) {
            view?.setAlerts(alerts)
        }
    }
}