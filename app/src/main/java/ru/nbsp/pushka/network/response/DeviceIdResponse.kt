package ru.nbsp.pushka.network.response

import com.google.gson.annotations.SerializedName

/**
 * Created by Dimorinny on 21.05.16.
 */
data class DeviceIdResponse(
        @SerializedName("id") val deviceId: String
)