package ru.nbsp.pushka.repository.alert

import ru.nbsp.pushka.mapper.presentation.alert.PresentationAlertMapper
import ru.nbsp.pushka.network.service.PushkaAlertsService
import ru.nbsp.pushka.presentation.core.model.alert.PresentationAlert
import ru.nbsp.pushka.util.SchedulersUtils
import rx.Observable
import java.util.*

/**
 * Created by Dimorinny on 28.02.16.
 */
class ApiAlertsRepository(
        val apiPushka: PushkaAlertsService,
        val alertMapper: PresentationAlertMapper,
        val schedulersUtils: SchedulersUtils) : AlertsRepository {

    override fun getAlerts(): Observable<List<PresentationAlert>> {
        return apiPushka
                .getAlerts()
                .map {
                    var result = ArrayList<PresentationAlert>()
                    for (alert in it.alerts) {
                        result.add(alertMapper.fromNetworkAlert(alert))
                    }
                    result
                }
                .compose(schedulersUtils.applySchedulers<List<PresentationAlert>>())
    }

    override fun getAlert(alertId: String): Observable<PresentationAlert> {
        return apiPushka.getAlert(alertId)
                .map {
                    alertMapper.fromNetworkAlert(it.alert)
                }
                .compose(schedulersUtils.applySchedulers<PresentationAlert>())
    }

    override fun getAlertsWithFilter(query: String): Observable<List<PresentationAlert>> {
        throw UnsupportedOperationException()
    }
}