package ru.nbsp.pushka.repository.device

import ru.nbsp.pushka.mapper.presentation.device.PresentationDeviceMapper
import ru.nbsp.pushka.network.service.PushkaDeviceService
import ru.nbsp.pushka.presentation.core.model.device.PresentationDevice
import ru.nbsp.pushka.util.SchedulersUtils
import rx.Observable
import java.util.*

/**
 * Created by Dimorinny on 31.03.16.
 */
class ApiDevicesRepository(
        val api: PushkaDeviceService,
        val deviceMapper: PresentationDeviceMapper,
        val schedulersUtils: SchedulersUtils) : DevicesRepository {

    override fun getDevices(): Observable<List<PresentationDevice>> {
        return api
                .getDevices()
                .map {
                    var result = ArrayList<PresentationDevice>()
                    for (device in it.devices) {
                        result.add(deviceMapper.fromNetworkDevice(device))
                    }
                    result
                }
                .compose(schedulersUtils.applySchedulers<List<PresentationDevice>>())
    }
}