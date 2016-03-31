package ru.nbsp.pushka.network.model.device

import com.google.gson.annotations.SerializedName

/**
 * Created by Dimorinny on 31.03.16.
 */
data class NetworkDevice(
        @SerializedName("id") val id: String,
        @SerializedName("name") val name: String,
        @SerializedName("type") val type: String,
        @SerializedName("token") val token: String,
        @SerializedName("enabled") val enabled: Boolean,
        @SerializedName("user_editable") val editable: Boolean
)