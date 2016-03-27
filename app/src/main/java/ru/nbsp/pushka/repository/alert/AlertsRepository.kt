package ru.nbsp.pushka.repository.alert

import ru.nbsp.pushka.presentation.core.model.alert.PresentationAlert
import rx.Observable

/**
 * Created by Dimorinny on 25.02.16.
 */
interface AlertsRepository {
    fun getAlerts(): Observable<List<PresentationAlert>>
    fun getAlert(alertId: String): Observable<PresentationAlert>
}