package ru.nbsp.pushka.network.model.alert

import com.google.gson.annotations.SerializedName

/**
 * Created by Dimorinny on 26.05.16.
 */
data class NetworkAlertOptions(
        @SerializedName("sound") val sound: Boolean
)