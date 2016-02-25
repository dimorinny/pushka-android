package ru.nbsp.pushka.repository.alert

import ru.nbsp.pushka.api.model.alert.Alert
import rx.Observable

/**
 * Created by Dimorinny on 25.02.16.
 */
interface AlertsRepository {
    fun getAlerts(): Observable<List<Alert>>
}