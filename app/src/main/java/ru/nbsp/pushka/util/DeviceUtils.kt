package ru.nbsp.pushka.util

import android.content.Context
import android.os.Build
import android.provider.Settings
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
    @Inject constructor(val context: Context,
                        val windowManager: WindowManager) {

    fun getDeviceUUID(): String {
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)

        val deviceId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID);
        val sizes = "${metrics.widthPixels} ${metrics.heightPixels}"

        val uuid = UUID(deviceId.hashCode().toLong(), sizes.hashCode().toLong())
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