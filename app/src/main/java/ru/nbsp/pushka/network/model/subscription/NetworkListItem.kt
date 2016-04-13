package ru.nbsp.pushka.network.model.subscription

import com.google.gson.annotations.SerializedName

/**
 * Created by Dimorinny on 12.04.16.
 */
data class NetworkListItem(
        @SerializedName("name") val name: String,
        @SerializedName("value") val value: String,
        @SerializedName("description") val description: String?,
        @SerializedName("image") val image: String?
)