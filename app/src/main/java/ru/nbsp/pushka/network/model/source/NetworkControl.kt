package ru.nbsp.pushka.network.model.source

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

/**
 * Created by Dimorinny on 05.03.16.
 */
data class NetworkControl(
        @SerializedName("type") val type: String,
        @SerializedName("title") val title: String,
        @SerializedName("attributes") val attributes: JsonObject
)