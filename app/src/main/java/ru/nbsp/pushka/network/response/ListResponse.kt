package ru.nbsp.pushka.network.response

import com.google.gson.annotations.SerializedName
import ru.nbsp.pushka.network.model.subscription.NetworkListItem

/**
 * Created by Dimorinny on 12.04.16.
 */
data class ListResponse(
        @SerializedName("list") val list: List<NetworkListItem>
)