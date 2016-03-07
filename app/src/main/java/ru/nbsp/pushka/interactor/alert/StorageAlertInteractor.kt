package ru.nbsp.pushka.interactor.alert

import ru.nbsp.pushka.data.DataManager
import ru.nbsp.pushka.mapper.data.alert.DataAlertMapper
import ru.nbsp.pushka.presentation.core.model.alert.PresentationAlert
import ru.nbsp.pushka.util.SchedulersUtils
import rx.Observable

/**
 * Created by Dimorinny on 02.03.16.
 */
class StorageAlertInteractor(val dataManager: DataManager,
                             val dataAlertMapper: DataAlertMapper,
                             val schedulersUtils: SchedulersUtils) : AlertInteractor {

    override fun saveAlerts(alerts: List<PresentationAlert>): Observable<List<PresentationAlert>> {
        dataManager.clearAlerts()
        dataManager.putAlerts(alerts.map { dataAlertMapper.fromPresentationAlert(it) })
        return Observable.just(alerts)
    }
}