package ru.nbsp.pushka.network.response

import com.google.gson.annotations.SerializedName
import ru.nbsp.pushka.network.model.user.Identity
import ru.nbsp.pushka.network.model.user.User

/**
 * Created by Dimorinny on 17.02.16.
 */
data class LoginResponse(
        @SerializedName("identity") val identity: Identity,
        @SerializedName("user") val user: User
)