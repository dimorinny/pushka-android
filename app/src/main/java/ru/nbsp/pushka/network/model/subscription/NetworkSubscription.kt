package ru.nbsp.pushka.network.model.subscription

import com.google.gson.annotations.SerializedName

/**
 * Created by Dimorinny on 26.02.16.
 */
data class NetworkSubscription(
        @SerializedName("name") val title: String,
        @SerializedName("src_id") val subscriptionId: String,
        @SerializedName("params") val params: Map<String, String>
)