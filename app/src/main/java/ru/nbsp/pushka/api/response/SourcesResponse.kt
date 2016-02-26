package ru.nbsp.pushka.api.response

import com.google.gson.annotations.SerializedName
import ru.nbsp.pushka.api.model.source.Source

/**
 * Created by Dimorinny on 26.02.16.
 */
data class SourcesResponse(
        @SerializedName("sources") val sources: List<Source>
)