package ru.nbsp.pushka.service.api

import android.app.Service
import android.content.Intent
import android.os.IBinder
import ru.nbsp.pushka.BaseApplication
import ru.nbsp.pushka.di.annotation.ApiRepository
import ru.nbsp.pushka.bus.RxBus
import ru.nbsp.pushka.bus.event.BaseEvent
import ru.nbsp.pushka.bus.event.alert.LoadAlertEvent
import ru.nbsp.pushka.bus.event.alert.LoadAlertsEvent
import ru.nbsp.pushka.interactor.alert.StorageAlertInteractor
import ru.nbsp.pushka.repository.alert.AlertsRepository
import ru.nbsp.pushka.service.BaseEventSubscriber
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
                .subscribe(object : BaseEventSubscriber(this, startId, bus) {
                    override fun error(t: Throwable): BaseEvent {
                        return LoadAlertEvent.Error(t)
                    }

                    override fun success(): BaseEvent {
                        return LoadAlertEvent.Success(alertId)
                    }
                })
    }

    private fun handleLoadAlertsCommand(startId: Int) {
        apiAlertsRepository.getAlerts()
                .flatMap {
                    storageAlertInteractor.saveAlerts(it)
                }
                .subscribe(object : BaseEventSubscriber(this, startId, bus) {
                    override fun error(t: Throwable): BaseEvent {
                        return LoadAlertsEvent.Error(t)
                    }

                    override fun success(): BaseEvent {
                        return LoadAlertsEvent.Success()
                    }
                })
    }
}