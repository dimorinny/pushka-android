package ru.nbsp.pushka.network.model.source

import com.google.gson.annotations.SerializedName

/**
 * Created by Dimorinny on 26.02.16.
 */
data class NetworkParam(
        @SerializedName("name") val name: String,
        @SerializedName("required") val required: Boolean,
        @SerializedName("control") val control: NetworkControl
)