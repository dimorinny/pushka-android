package ru.nbsp.pushka.presentation.core.model.device

/**
 * Created by Dimorinny on 31.03.16.
 */
data class PresentationDevice(
        val id: String,
        val name: String,
        val type: String,
        val token: String,
        val enabled: Boolean,
        val editable: Boolean
)