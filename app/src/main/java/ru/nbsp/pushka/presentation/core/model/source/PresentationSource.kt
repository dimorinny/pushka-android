package ru.nbsp.pushka.presentation.core.model.source

import java.io.Serializable

/**
 * Created by Dimorinny on 26.02.16.
 */
data class PresentationSource(
        val id: String,
        val params: List<PresentationParam>,
        val name: String,
        val description: String,
        val category: String,
        val icon: String,
        val color: String
) : Serializable