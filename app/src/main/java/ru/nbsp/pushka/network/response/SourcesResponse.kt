package ru.nbsp.pushka.network.response

import com.google.gson.annotations.SerializedName
import ru.nbsp.pushka.network.model.source.NetworkSource

/**
 * Created by Dimorinny on 26.02.16.
 */
data class SourcesResponse(
        @SerializedName("sources") val sources: List<NetworkSource>
)