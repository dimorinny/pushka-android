package ru.nbsp.pushka.repository.alert

import ru.nbsp.pushka.data.DataManager
import ru.nbsp.pushka.mapper.presentation.alert.PresentationAlertMapper
import ru.nbsp.pushka.presentation.core.model.alert.PresentationAlert
import rx.Observable
import java.util.*

/**
 * Created by Dimorinny on 02.03.16.
 */
class StorageAlertsRepository(
        val dataManager: DataManager,
        val alertMapper: PresentationAlertMapper) : AlertsRepository {

    override fun getAlerts(): Observable<List<PresentationAlert>> {
        return dataManager.getAlertsObservable()
                .map {
                    val result = ArrayList<PresentationAlert>()
                    for (alert in it) {
                        result.add(alertMapper.fromDataAlert(alert))
                    }
                    result
                }
    }

    override fun getMoreAlerts(firstItemTime: Long, offset: Int): Observable<List<PresentationAlert>> {
        throw UnsupportedOperationException()
    }

    override fun getAlertsWithFilter(query: String): Observable<List<PresentationAlert>> {
        return dataManager.getAlertsWithFilter(query)
                .map {
                    val result = ArrayList<PresentationAlert>()
                    for (alert in it) {
                        result.add(alertMapper.fromDataAlert(alert))
                    }
                    result
                }
    }

    override fun getAlert(alertId: String): Observable<PresentationAlert> {
        return dataManager.getAlertObservable(alertId)
                .map {
                    alertMapper.fromDataAlert(it)
                }
    }
}