package ru.nbsp.pushka.interactor.device

import ru.nbsp.pushka.presentation.core.model.device.PresentationDevice
import rx.Observable

/**
 * Created by Dimorinny on 20.04.16.
 */
interface StorageDeviceInteractor {
    fun saveDevices(devices: List<PresentationDevice>): Observable<List<PresentationDevice>>
}