package ru.nbsp.pushka.network.model.user

import com.google.gson.annotations.SerializedName

/**
 * Created by Dimorinny on 17.02.16.
 */
data class NetworkIdentity(
        @SerializedName("access_token") val accessToken: String,
        @SerializedName("refresh_token") val refreshToken: String,
        @SerializedName("expires") val expires: Int
)