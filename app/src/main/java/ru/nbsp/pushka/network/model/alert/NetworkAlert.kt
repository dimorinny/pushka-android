package ru.nbsp.pushka.network.model.alert

import com.google.gson.annotations.SerializedName

/**
 * Created by Dimorinny on 06.03.16.
 */
data class NetworkAlert(
        @SerializedName("_id") val id: String,
        @SerializedName("notification") val notification: NetworkNotification
)