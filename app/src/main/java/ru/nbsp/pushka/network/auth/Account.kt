package ru.nbsp.pushka.network.auth

import com.google.gson.annotations.SerializedName

/**
 * Created by Dimorinny on 20.02.16.
 */
data class Account(
    @SerializedName("user_id")
    val userId: String,

    @SerializedName("first_name")
    val firstName: String,

    @SerializedName("second_name")
    val secondName: String,

    @SerializedName("photo")
    val photo: String,

    @SerializedName("access_token")
    val accessToken: String,

    @SerializedName("refresh_token")
    val refreshToken: String,

    @SerializedName("expire_timestamp")
    val expiredTimestamp: Long
)