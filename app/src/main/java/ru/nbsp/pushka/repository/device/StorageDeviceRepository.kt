package ru.nbsp.pushka.repository.device

import ru.nbsp.pushka.data.DataManager
import ru.nbsp.pushka.mapper.presentation.device.PresentationDeviceMapper
import ru.nbsp.pushka.presentation.core.model.device.PresentationDevice
import rx.Observable
import java.util.*

/**
 * Created by Dimorinny on 20.04.16.
 */
class StorageDeviceRepository(
        val dataManager: DataManager,
        val deviceMapper: PresentationDeviceMapper) : DeviceRepository {

    override fun getDevices(): Observable<List<PresentationDevice>> {
        return dataManager.getDevicesObservable()
                .map {
                    var result = ArrayList<PresentationDevice>()
                    for (device in it) {
                        result.add(deviceMapper.fromDataDevice(device))
                    }
                    result
                }
    }
}