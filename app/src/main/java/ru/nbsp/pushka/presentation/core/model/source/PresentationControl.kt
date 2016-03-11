package ru.nbsp.pushka.presentation.core.model.source

import com.google.gson.JsonObject

/**
 * Created by Dimorinny on 05.03.16.
 */
data class PresentationControl(
        val type: String,
        val title: String,
        val options: JsonObject
)