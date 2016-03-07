package ru.nbsp.pushka.presentation.core.model.source

/**
 * Created by Dimorinny on 26.02.16.
 */
data class PresentationParam(
        val name: String,
        val required: Boolean,
        val control: PresentationControl
)