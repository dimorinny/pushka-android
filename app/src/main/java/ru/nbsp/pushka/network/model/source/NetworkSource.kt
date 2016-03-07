package ru.nbsp.pushka.network.model.source

import com.google.gson.annotations.SerializedName

/**
 * Created by Dimorinny on 26.02.16.
 */
data class NetworkSource(
        @SerializedName("_id") val id: String,
        @SerializedName("params") val params: List<NetworkParam>,
        @SerializedName("name") val name: String,
        @SerializedName("description") val description: String,
        @SerializedName("category") val category: String
)