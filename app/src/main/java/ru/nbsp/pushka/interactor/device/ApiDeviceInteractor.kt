package ru.nbsp.pushka.interactor.device

import rx.Observable

/**
 * Created by Dimorinny on 31.03.16.
 */
interface ApiDeviceInteractor {
    fun addGcmDevice(token: String): Observable<Any>
    fun removeGcmDevice(): Observable<String>
    fun removeDevice(deviceId: String): Observable<String>
}