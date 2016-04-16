package ru.nbsp.pushka.network.response

import com.google.gson.annotations.SerializedName
import ru.nbsp.pushka.network.model.user.NetworkIdentity

/**
 * Created by Dimorinny on 16.04.16.
 */
data class RefreshTokenResponse(
    @SerializedName("identity") val identity: NetworkIdentity
)