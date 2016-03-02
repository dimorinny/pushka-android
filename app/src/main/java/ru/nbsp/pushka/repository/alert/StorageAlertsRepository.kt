package ru.nbsp.pushka.repository.alert

import ru.nbsp.pushka.data.DataManager
import ru.nbsp.pushka.data.entity.Alert
import ru.nbsp.pushka.util.SchedulersUtils
import rx.Observable

/**
 * Created by Dimorinny on 02.03.16.
 */
class StorageAlertsRepository(
        val dataManager: DataManager,
        val schedulersUtils: SchedulersUtils) : AlertsRepository {

    override fun getAlerts(): Observable<List<Alert>> {
        return dataManager.getAletsObservable()
                .take(1)
                .compose(schedulersUtils.applySchedulers<List<Alert>>())
    }
}