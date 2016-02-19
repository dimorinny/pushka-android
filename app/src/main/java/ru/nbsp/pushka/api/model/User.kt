package ru.nbsp.pushka.api.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Dimorinny on 17.02.16.
 */
data class User(
        @SerializedName("first_name") val firstName: String,
        @SerializedName("last_name") val lastName: String,
        @SerializedName("photo") val photo: String
)