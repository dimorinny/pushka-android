package ru.nbsp.pushka.network.response

import com.google.gson.annotations.SerializedName
import ru.nbsp.pushka.network.model.user.NetworkIdentity
import ru.nbsp.pushka.network.model.user.NetworkUser

/**
 * Created by Dimorinny on 17.02.16.
 */
data class LoginResponse(
        @SerializedName("identity") val identity: NetworkIdentity,
        @SerializedName("user") val user: NetworkUser
)