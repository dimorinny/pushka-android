package ru.nbsp.pushka.interactor.device

import ru.nbsp.pushka.data.DataManager
import ru.nbsp.pushka.mapper.data.device.DataDeviceMapper
import ru.nbsp.pushka.presentation.core.model.device.PresentationDevice
import rx.Observable

/**
 * Created by Dimorinny on 20.04.16.
 */
class StorageDeviceInteractorImpl(
        val dataManager: DataManager,
        val deviceMapper: DataDeviceMapper) : StorageDeviceInteractor {

    override fun saveDevices(devices: List<PresentationDevice>): Observable<List<PresentationDevice>> {
        dataManager.clearDevices()
        dataManager.putDevices(devices.map { deviceMapper.fromPresentationDevice(it) })
        return Observable.just(devices)
    }
}