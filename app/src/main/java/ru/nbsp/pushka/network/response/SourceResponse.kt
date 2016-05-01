package ru.nbsp.pushka.network.response

import com.google.gson.annotations.SerializedName
import ru.nbsp.pushka.network.model.source.NetworkSource

/**
 * Created by Dimorinny on 01.05.16.
 */
data class SourceResponse(
        @SerializedName("source") val source: NetworkSource
)