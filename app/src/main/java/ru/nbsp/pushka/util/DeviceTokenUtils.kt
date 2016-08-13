package ru.nbsp.pushka.util

import android.content.Context
import android.support.v4.content.ContextCompat
import ru.nbsp.pushka.R
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Dimorinny on 31.03.16.
 */
@Singleton
class DeviceTokenUtils @Inject constructor(val context: Context) {

    data class DeviceInfo(val name: String, val color: Int, val icon: Int)

    private val deviceNames = mapOf(
            "android" to DeviceInfo("Android", ContextCompat.getColor(context, R.color.android), R.drawable.ic_android_white_24dp),
            "telegram" to DeviceInfo("Telegram", ContextCompat.getColor(context, R.color.telegram), R.drawable.ic_telegram_white_24dp),
            "web" to DeviceInfo("Google Chrome", ContextCompat.getColor(context, R.color.chrome), R.drawable.ic_chrome_white_24dp)
    )

    fun getDeviceInfoById(id: String): DeviceInfo {
        return deviceNames[id]!!
    }
}