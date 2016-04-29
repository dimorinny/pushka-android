package ru.nbsp.pushka.network.response

import com.google.gson.annotations.SerializedName
import ru.nbsp.pushka.network.model.subscription.NetworkSubscription

/**
 * Created by Dimorinny on 27.04.16.
 */
data class SubscribeResponse(
        @SerializedName("subscription") val subscription: NetworkSubscription
)