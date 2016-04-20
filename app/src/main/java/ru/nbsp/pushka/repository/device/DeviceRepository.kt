package ru.nbsp.pushka.repository.device

import ru.nbsp.pushka.presentation.core.model.device.PresentationDevice
import rx.Observable

/**
 * Created by Dimorinny on 31.03.16.
 */
interface DeviceRepository {
    fun getDevices(): Observable<List<PresentationDevice>>
}