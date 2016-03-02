package ru.nbsp.pushka.interactor.alert

import ru.nbsp.pushka.data.entity.Alert
import rx.Observable

/**
 * Created by Dimorinny on 02.03.16.
 */
interface AlertInteractor {
    fun saveAlerts(alerts: List<Alert>): Observable<List<Alert>>
}