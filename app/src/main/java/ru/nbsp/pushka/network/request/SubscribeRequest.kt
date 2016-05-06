package ru.nbsp.pushka.network.request

import com.google.gson.annotations.SerializedName

/**
 * Created by Dimorinny on 27.02.16.
 */
data class SubscribeRequest(
        @SerializedName("src_id") val srcId: String,
        @SerializedName("sound") val sound: Boolean,
        @SerializedName("notification") val notification: Boolean,
        @SerializedName("params") val params: Map<String, String?>
)