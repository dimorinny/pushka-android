package ru.nbsp.pushka.api.model.source

import com.google.gson.annotations.SerializedName

/**
 * Created by Dimorinny on 26.02.16.
 */
data class Source(
        @SerializedName("_id") val id: String,
        @SerializedName("params") val params: List<Param>,
        @SerializedName("name") val name: String,
        @SerializedName("description") val description: String,
        @SerializedName("handler_id") val handlerId: String
)