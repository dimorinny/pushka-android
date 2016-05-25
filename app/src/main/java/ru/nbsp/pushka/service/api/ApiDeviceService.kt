package ru.nbsp.pushka.service.api

import android.app.Service
import android.content.Intent
import android.os.IBinder
import ru.nbsp.pushka.BaseApplication
import ru.nbsp.pushka.di.annotation.ApiRepository
import ru.nbsp.pushka.bus.RxBus
import ru.nbsp.pushka.bus.event.BaseEvent
import ru.nbsp.pushka.bus.event.device.LoadDevicesEvent
import ru.nbsp.pushka.bus.event.device.RemoveGcmDeviceEvent
import ru.nbsp.pushka.interactor.device.ApiDeviceInteractor
import ru.nbsp.pushka.interactor.device.StorageDeviceInteractor
import ru.nbsp.pushka.repository.device.DeviceRepository
import ru.nbsp.pushka.service.BaseEventSubscriber
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

/**
 * Created by Dimorinny on 20.04.16.
 */
class ApiDeviceService : Service() {

    @Inject
    lateinit var bus: RxBus

    @field:[Inject ApiRepository]
    lateinit var apiDeviceRepository: DeviceRepository

    @Inject
    lateinit var storageDeviceInteractor: StorageDeviceInteractor

    @Inject
    lateinit var apiDeviceInteractor: ApiDeviceInteractor

    private val subscription = CompositeSubscription()

    companion object {
        const val ARG_SERVICE_COMMAND = "arg_service_command"
        const val COMMAND_LOAD_DEVICES = "command_load_devices"
        const val COMMAND_REMOVE_DEVICE = "command_remove_device"
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
            COMMAND_LOAD_DEVICES -> {
                handleLoadDevicesCommand(startId)
            }
            COMMAND_REMOVE_DEVICE -> {
                handleRemoveDeviceCommand(startId)
            }
        }

        return START_NOT_STICKY
    }

    private fun handleRemoveDeviceCommand(startId: Int) {
        apiDeviceInteractor.removeGcmDevice()
                .flatMap {
                    storageDeviceInteractor.removeDevice(it)
                }
                .subscribe(object : BaseEventSubscriber(this, startId, bus) {
                    override fun error(t: Throwable): BaseEvent {
                        return RemoveGcmDeviceEvent.Error(t)
                    }

                    override fun success(): BaseEvent {
                        return RemoveGcmDeviceEvent.Success()
                    }
                })
    }

    private fun handleLoadDevicesCommand(startId: Int) {
        apiDeviceRepository.getDevices()
                .flatMap {
                    storageDeviceInteractor.saveDevices(it)
                }
                .subscribe(object : BaseEventSubscriber(this, startId, bus) {
                    override fun error(t: Throwable): BaseEvent {
                        return LoadDevicesEvent.Error(t)
                    }

                    override fun success(): BaseEvent {
                        return LoadDevicesEvent.Success()
                    }
                })
    }
}