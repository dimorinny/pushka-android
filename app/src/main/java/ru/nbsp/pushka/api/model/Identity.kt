package ru.nbsp.pushka.api.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Dimorinny on 17.02.16.
 */
data class Identity(
        @SerializedName("access_token") val accessToken: String,
        @SerializedName("refresh_token") val refreshToken: String,
        @SerializedName("expires") val expires: Int
)