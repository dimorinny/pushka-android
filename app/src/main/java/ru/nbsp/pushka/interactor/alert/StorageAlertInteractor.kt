package ru.nbsp.pushka.interactor.alert

import ru.nbsp.pushka.data.DataManager
import ru.nbsp.pushka.data.model.alert.Alert
import ru.nbsp.pushka.util.SchedulersUtils
import rx.Observable

/**
 * Created by Dimorinny on 02.03.16.
 */
class StorageAlertInteractor(val dataManager: DataManager,
                             val schedulersUtils: SchedulersUtils) : AlertInteractor {

    override fun saveAlerts(alerts: List<Alert>): Observable<List<Alert>> {
        dataManager.clearAlerts()
        dataManager.putAlerts(alerts)
        return Observable.just(alerts)
    }
}