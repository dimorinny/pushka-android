package ru.nbsp.pushka.network.response

import com.google.gson.annotations.SerializedName
import ru.nbsp.pushka.network.model.subscription.NetworkSubscription

/**
 * Created by Dimorinny on 14.03.16.
 */
data class SubscriptionsResponse(
        @SerializedName("subs") val subscriptions: List<NetworkSubscription>
)
