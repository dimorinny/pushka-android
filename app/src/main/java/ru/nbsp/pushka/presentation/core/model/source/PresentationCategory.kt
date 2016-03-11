package ru.nbsp.pushka.presentation.core.model.source

import java.io.Serializable

/**
 * Created by Dimorinny on 06.03.16.
 */
data class PresentationCategory(
        val id: String,
        val name: String,
        val image: String) : Serializable