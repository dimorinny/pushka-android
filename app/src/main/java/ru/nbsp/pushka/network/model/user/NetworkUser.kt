package ru.nbsp.pushka.network.model.user

import com.google.gson.annotations.SerializedName

/**
 * Created by Dimorinny on 17.02.16.
 */
data class NetworkUser(
        @SerializedName("first_name") val firstName: String,
        @SerializedName("last_name") val lastName: String,
        @SerializedName("photo") val photo: String,
        @SerializedName("user_id") val userId: String
)