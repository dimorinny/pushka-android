package ru.nbsp.pushka.presentation.core.model.source

import java.io.Serializable

/**
 * Created by Dimorinny on 26.02.16.
 */
data class PresentationParam(
        val name: String,
        val required: Boolean,
        val control: PresentationControl
) : Serializable