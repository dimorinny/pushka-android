package ru.nbsp.pushka.api.response

import com.google.gson.annotations.SerializedName
import ru.nbsp.pushka.api.model.user.Identity
import ru.nbsp.pushka.api.model.user.User

/**
 * Created by Dimorinny on 17.02.16.
 */
data class LoginResponse(
        @SerializedName("identity") val identity: Identity,
        @SerializedName("user") val user: User
)