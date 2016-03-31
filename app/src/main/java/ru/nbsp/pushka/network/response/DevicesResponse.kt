package ru.nbsp.pushka.network.response

import com.google.gson.annotations.SerializedName
import ru.nbsp.pushka.network.model.device.NetworkDevice

/**
 * Created by Dimorinny on 31.03.16.
 */
data class DevicesResponse(
        @SerializedName("devices") val devices: List<NetworkDevice>
)