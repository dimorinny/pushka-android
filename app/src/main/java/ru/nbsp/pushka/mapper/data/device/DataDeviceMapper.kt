package ru.nbsp.pushka.mapper.data.device

import ru.nbsp.pushka.data.model.device.DataDevice
import ru.nbsp.pushka.network.model.device.NetworkDevice
import ru.nbsp.pushka.presentation.core.model.device.PresentationDevice
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Dimorinny on 31.03.16.
 */
@Singleton
class DataDeviceMapper @Inject constructor() {
    fun fromNetworkDevice(networkDevice: NetworkDevice): DataDevice {
        return DataDevice(
                id = networkDevice.id,
                name = networkDevice.name,
                type = networkDevice.type,
                token = networkDevice.token,
                enabled = networkDevice.enabled,
                editable = networkDevice.editable
        )
    }

    fun fromPresentationDevice(presentationDevice: PresentationDevice): DataDevice {
        return DataDevice(
                id = presentationDevice.id,
                name = presentationDevice.name,
                type = presentationDevice.type,
                token = presentationDevice.token,
                enabled = presentationDevice.enabled,
                editable = presentationDevice.editable
        )
    }
}