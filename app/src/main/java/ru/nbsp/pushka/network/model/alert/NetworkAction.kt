package ru.nbsp.pushka.network.model.alert

import com.google.gson.annotations.SerializedName

/**
 * Created by Dimorinny on 11.03.16.
 */
data class NetworkAction(
        @SerializedName("type") val type: String,
        @SerializedName("value") val value: String
)