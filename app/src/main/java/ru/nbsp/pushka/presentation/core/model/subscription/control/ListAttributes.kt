package ru.nbsp.pushka.presentation.core.model.subscription.control

import com.google.gson.annotations.SerializedName

/**
 * Created by Dimorinny on 12.04.16.
 */
data class ListAttributes(
        @SerializedName("list_id") val listId: String,
        @SerializedName("source_id") val sourceId: String
)