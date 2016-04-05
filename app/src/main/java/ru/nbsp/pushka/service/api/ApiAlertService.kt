package ru.nbsp.pushka.service.api

import android.app.Service
import android.content.Intent
import android.os.IBinder
import ru.nbsp.pushka.BaseApplication
import ru.nbsp.pushka.annotation.ApiRepository
import ru.nbsp.pushka.bus.RxBus
import ru.nbsp.pushka.bus.event.alert.LoadAlertEvent
import ru.nbsp.pushka.bus.event.alert.LoadAlertsEvent
import ru.nbsp.pushka.interactor.alert.StorageAlertInteractor
import ru.nbsp.pushka.presentation.core.model.alert.PresentationAlert
import ru.nbsp.pushka.repository.alert.AlertsRepository
import rx.Subscriber
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

/**
 * Created by Dimorinny on 31.03.16.
 */
class ApiAlertService : Service() {

    @Inject
    lateinit var bus: RxBus

    @field:[Inject ApiRepository]
    lateinit var apiAlertsRepository: AlertsRepository

    @Inject
    lateinit var storageAlertInteractor: StorageAlertInteractor

    private val subscription = CompositeSubscription()

    companion object {
        const val ARG_SERVICE_COMMAND = "arg_service_command"
        const val ARG_ALERT_ID = "arg_alert_id"
        const val COMMAND_LOAD_ALERTS = "command_load_alerts"
        const val COMMAND_LOAD_ALERT = "command_load_alert"
    }

    override fun onBind(intent: Intent?): IBinder? { return null }

    override fun onCreate() {
        super.onCreate()
        BaseApplication.graph.inject(this)
    }

    override fun onDestroy() {
        subscription.unsubscribe()
        super.onDestroy()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent == null) {
            return START_NOT_STICKY
        }

        val command = intent.getStringExtra(ARG_SERVICE_COMMAND)

        when (command) {
            COMMAND_LOAD_ALERTS -> {
                handleLoadAlertsCommand(startId)
            }
            COMMAND_LOAD_ALERT -> {
                handleLoadAlertCommand(intent, startId)
            }
        }

        return START_NOT_STICKY
    }

    private fun handleLoadAlertCommand(intent: Intent, startId: Int) {
        val alertId = intent.getStringExtra(ARG_ALERT_ID)

        apiAlertsRepository.getAlert(alertId)
                .flatMap {
                    storageAlertInteractor.saveAlert(it)
                }
                .subscribe(LoadAlertSubscriber(startId))
    }

    private fun handleLoadAlertsCommand(startId: Int) {
        apiAlertsRepository.getAlerts()
                .flatMap {
                    storageAlertInteractor.saveAlerts(it)
                }
                .subscribe(LoadAlertsSubscriber(startId))
    }

    inner class LoadAlertsSubscriber(val startId: Int) : Subscriber<List<PresentationAlert>>() {
        override fun onCompleted() {}

        override fun onError(t: Throwable) {
            bus.post(LoadAlertsEvent.Error(t) as LoadAlertsEvent)
            stopSelf(startId)
        }

        override fun onNext(alerts: List<PresentationAlert>) {
            bus.post(LoadAlertsEvent.Success() as LoadAlertsEvent)
            stopSelf(startId)
        }
    }

    inner class LoadAlertSubscriber(val startId: Int) : Subscriber<PresentationAlert>() {
        override fun onCompleted() {}

        override fun onError(t: Throwable) {
            bus.post(LoadAlertEvent.Error(t) as LoadAlertEvent)
            stopSelf(startId)
        }

        override fun onNext(alert: PresentationAlert) {
            bus.post(LoadAlertEvent.Success(alert.id) as LoadAlertEvent)
            stopSelf(startId)
        }
    }
}