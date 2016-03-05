package ru.nbsp.pushka.data.model.source

import com.google.gson.JsonArray
import com.google.gson.annotations.SerializedName

/**
 * Created by Dimorinny on 05.03.16.
 */
data class Control(
        @SerializedName("type") val type: String,
        @SerializedName("title") val title: String,
        @SerializedName("options") val options: JsonArray
)