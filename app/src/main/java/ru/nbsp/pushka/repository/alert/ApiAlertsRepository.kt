package ru.nbsp.pushka.repository.alert

import ru.nbsp.pushka.data.model.alert.Alert
import ru.nbsp.pushka.network.service.PushkaAlertsService
import ru.nbsp.pushka.util.SchedulersUtils
import rx.Observable

/**
 * Created by Dimorinny on 28.02.16.
 */
class ApiAlertsRepository(
        val apiPushka: PushkaAlertsService,
        val schedulersUtils: SchedulersUtils) : AlertsRepository {

    override fun getAlerts(): Observable<List<Alert>> {
        return apiPushka
                .getAlerts()
                .map { it.alerts }
                .compose(schedulersUtils.applySchedulers<List<Alert>>())
    }
}