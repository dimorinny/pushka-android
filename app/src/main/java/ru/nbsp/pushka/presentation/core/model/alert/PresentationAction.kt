package ru.nbsp.pushka.presentation.core.model.alert

import java.io.Serializable

/**
 * Created by Dimorinny on 15.03.16.
 */
data class PresentationAction(
        val type: String,
        val value: String
) : Serializable