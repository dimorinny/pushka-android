package ru.nbsp.pushka.network.model.subscription

import com.google.gson.annotations.SerializedName

/**
 * Created by Dimorinny on 26.02.16.
 */
data class NetworkSubscription(
        @SerializedName("_id") val id: String,
        @SerializedName("src_id") val sourceId: String,
        @SerializedName("name") val title: String,
        @SerializedName("icon") val icon: String,
        @SerializedName("color") val color: String,
        @SerializedName("source_name") val sourceTitle: String,
        @SerializedName("params") val params: Map<String, String>
)