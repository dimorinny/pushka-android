package ru.nbsp.pushka.util

import android.os.Build
import android.telephony.TelephonyManager
import android.util.DisplayMetrics
import android.view.WindowManager
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Dimorinny on 31.03.16.
 */
@Singleton
class DeviceUtils
    @Inject constructor(val telephonyManager: TelephonyManager,
                        val windowManager: WindowManager) {

    fun getDeviceUUID(): String {
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)

        val deviceId = telephonyManager.deviceId
        val serial = telephonyManager.simSerialNumber
        val sizes = "${metrics.widthPixels} ${metrics.heightPixels}"

        val uuid = UUID(deviceId.hashCode().toLong(), (serial.hashCode().toLong() shl 32) or sizes.hashCode().toLong())
        return uuid.toString()
    }

    fun getDeviceName(): String {
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL

        return if (model.startsWith(manufacturer)) {
            model
        } else {
            "$manufacturer $model"
        }
    }
}