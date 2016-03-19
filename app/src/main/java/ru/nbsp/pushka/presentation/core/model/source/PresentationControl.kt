package ru.nbsp.pushka.presentation.core.model.source

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import ru.nbsp.pushka.presentation.subscription.params.control.Control

/**
 * Created by Dimorinny on 05.03.16.
 */
data class PresentationControl(
        val type: String,
        val title: String,
        val attributes: JsonObject
)