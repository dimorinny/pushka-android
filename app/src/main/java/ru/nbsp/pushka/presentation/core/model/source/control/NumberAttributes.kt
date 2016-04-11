package ru.nbsp.pushka.presentation.core.model.source.control

import com.google.gson.annotations.SerializedName

/**
 * Created by Dimorinny on 11.04.16.
 */
data class NumberAttributes(
        @SerializedName("min_value") val minValue: String,
        @SerializedName("max_value") val maxValue: String,
        @SerializedName("default_value") val default: String,
        @SerializedName("precision") val precision: String
)