package ru.nbsp.pushka.network.model.alert

import com.google.gson.annotations.SerializedName

/**
 * Created by Dimorinny on 25.02.16.
 */
data class Alert(
        @SerializedName("title") val title: String,
        @SerializedName("description") val text: String,
        @SerializedName("thumbnail_url") val photo: String?,
        val sourceImage: String,
        val sourceTitle: String,
        @SerializedName("url") val shareLink: String
)