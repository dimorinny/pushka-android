package ru.nbsp.pushka.data.model.source

import com.google.gson.annotations.SerializedName

/**
 * Created by Dimorinny on 26.02.16.
 */
data class Param(
        @SerializedName("name") val name: String,
        @SerializedName("required") val required: Boolean,
        @SerializedName("control") val control: Control
)