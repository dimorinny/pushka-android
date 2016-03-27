package ru.nbsp.pushka.network.response

import com.google.gson.annotations.SerializedName
import ru.nbsp.pushka.network.model.alert.NetworkAlert

/**
 * Created by Dimorinny on 24.03.16.
 */
data class AlertResponse(
        @SerializedName("alert") val alert: NetworkAlert
)