package ru.nbsp.pushka.network.request

import com.google.gson.annotations.SerializedName

/**
 * Created by Dimorinny on 27.02.16.
 */
data class SubscribeRequest(
        @SerializedName("src_id") val srcId: String,
        @SerializedName("params") val params: Map<String, String?>
)