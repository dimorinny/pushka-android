package ru.nbsp.pushka.network.response

import com.google.gson.annotations.SerializedName

/**
 * Created by Dimorinny on 06.05.16.
 */
class SubscriptionIdResponse(
        @SerializedName("subscription_id") val subscriptionId: String
)