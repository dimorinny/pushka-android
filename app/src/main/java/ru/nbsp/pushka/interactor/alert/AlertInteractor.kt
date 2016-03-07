package ru.nbsp.pushka.interactor.alert

import ru.nbsp.pushka.presentation.core.model.alert.PresentationAlert
import rx.Observable

/**
 * Created by Dimorinny on 02.03.16.
 */
interface AlertInteractor {
    fun saveAlerts(alerts: List<PresentationAlert>): Observable<List<PresentationAlert>>
}