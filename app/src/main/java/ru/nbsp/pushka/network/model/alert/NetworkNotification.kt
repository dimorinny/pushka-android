package ru.nbsp.pushka.network.model.alert

import com.google.gson.annotations.SerializedName

/**
 * Created by Dimorinny on 11.03.16.
 */
data class NetworkNotification(
        @SerializedName("description") val description: String,
        @SerializedName("color") val color: String,
        @SerializedName("title") val title: String,
        @SerializedName("image") val image: String,
        @SerializedName("icon") val icon: String,
        @SerializedName("action") val actions: List<NetworkAction>
)