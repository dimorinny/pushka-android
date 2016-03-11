package ru.nbsp.pushka.network.response

import com.google.gson.annotations.SerializedName
import ru.nbsp.pushka.network.model.source.NetworkCategory

/**
 * Created by Dimorinny on 11.03.16.
 */
data class CategoriesResponse(
        @SerializedName("categories") val categories: List<NetworkCategory>
)