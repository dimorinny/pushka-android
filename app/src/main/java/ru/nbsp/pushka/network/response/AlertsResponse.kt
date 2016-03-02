package ru.nbsp.pushka.network.response

import com.google.gson.annotations.SerializedName
import ru.nbsp.pushka.data.entity.Alert

/**
 * Created by Dimorinny on 28.02.16.
 */
data class AlertsResponse(
        @SerializedName("alerts") val alerts: List<Alert>
)