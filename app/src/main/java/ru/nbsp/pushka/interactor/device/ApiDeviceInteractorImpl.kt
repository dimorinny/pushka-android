package ru.nbsp.pushka.interactor.device

import ru.nbsp.pushka.network.service.PushkaDeviceService
import ru.nbsp.pushka.util.DeviceUtils
import ru.nbsp.pushka.util.SchedulersUtils
import rx.Observable

/**
 * Created by Dimorinny on 31.03.16.
 */
class ApiDeviceInteractorImpl(
        val api: PushkaDeviceService,
        val deviceUtils: DeviceUtils,
        val schedulersUtils: SchedulersUtils) : ApiDeviceInteractor {

    companion object {
        const val ANDROID_TYPE = "android"
    }

    override fun addGcmDevice(token: String): Observable<Any> {
        return api.putDevice(deviceUtils.getDeviceUUID(),
                ANDROID_TYPE,
                token,
                deviceUtils.getDeviceName())
                .compose(schedulersUtils.applySchedulers<Any>())
    }

    override fun removeGcmDevice(): Observable<String> {
        return api.deleteDevice(deviceUtils.getDeviceUUID())
                .map { it.deviceId }
                .compose(schedulersUtils.applySchedulers<String>())
    }

    override fun removeDevice(deviceId: String): Observable<String> {
        return api.deleteDevice(deviceId)
                .map { it.deviceId }
                .compose(schedulersUtils.applySchedulers<String>())
    }
}