package ru.nbsp.pushka.interactor.alert

import ru.nbsp.pushka.data.DataManager
import ru.nbsp.pushka.mapper.data.alert.DataAlertMapper
import ru.nbsp.pushka.presentation.core.model.alert.PresentationAlert
import rx.Observable

/**
 * Created by Dimorinny on 02.03.16.
 */
class StorageAlertInteractorImpl(val dataManager: DataManager,
                                 val dataAlertMapper: DataAlertMapper) : StorageAlertInteractor {

    override fun saveAlerts(alerts: List<PresentationAlert>): Observable<List<PresentationAlert>> {
        dataManager.clearAlerts()
        dataManager.putAlerts(alerts.map { dataAlertMapper.fromPresentationAlert(it) })
        return Observable.just(alerts)
    }
}