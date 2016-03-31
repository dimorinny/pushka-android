package ru.nbsp.pushka.mapper.presentation.device

import ru.nbsp.pushka.data.model.device.DataDevice
import ru.nbsp.pushka.network.model.device.NetworkDevice
import ru.nbsp.pushka.presentation.core.model.device.PresentationDevice
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Dimorinny on 31.03.16.
 */
@Singleton
class PresentationDeviceMapper @Inject constructor() {
    fun fromNetworkDevice(networkDevice: NetworkDevice): PresentationDevice {
        return PresentationDevice(
                id = networkDevice.id,
                name = networkDevice.name,
                type = networkDevice.type,
                token = networkDevice.token,
                enabled = networkDevice.enabled,
                editable = networkDevice.editable
        )
    }

    fun fromDataDevice(dataDevice: DataDevice): PresentationDevice {
        return PresentationDevice(
                id = dataDevice.id,
                name = dataDevice.name,
                type = dataDevice.type,
                token = dataDevice.token,
                enabled = dataDevice.enabled,
                editable = dataDevice.editable
        )
    }
}