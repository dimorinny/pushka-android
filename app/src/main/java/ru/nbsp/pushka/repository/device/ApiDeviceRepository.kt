package ru.nbsp.pushka.repository.device

import ru.nbsp.pushka.network.service.PushkaDeviceService
import ru.nbsp.pushka.util.SchedulersUtils

/**
 * Created by Dimorinny on 31.03.16.
 */
class ApiDeviceRepository(
        val api: PushkaDeviceService,
        val schedulersUtils: SchedulersUtils) : DeviceRepository {
}