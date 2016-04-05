package ru.nbsp.pushka.network.model.source

import com.google.gson.annotations.SerializedName

/**
 * Created by Dimorinny on 06.03.16.
 */
data class NetworkCategory(
        @SerializedName("_id") val id: String,
        @SerializedName("name") val name: String,
        @SerializedName("icon") val icon: String,
        @SerializedName("color") val color: String
)