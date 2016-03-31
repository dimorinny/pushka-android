package ru.nbsp.pushka.util

import ru.nbsp.pushka.R
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Dimorinny on 31.03.16.
 */
@Singleton
class DeviceTokenUtils @Inject constructor() {

    data class DeviceInfo(val name: String, val color: String, val icon: Int)

    private val deviceNames = mapOf(
            Pair("android", DeviceInfo("Android", "#43A047", R.drawable.ic_android_white_24dp)),
            Pair("telegram", DeviceInfo("Telegram", "#43A047", R.drawable.ic_telegram_white_24dp))
    )

    fun getDeviceNameById(id: String): DeviceInfo {
        return deviceNames[id]!!
    }
}