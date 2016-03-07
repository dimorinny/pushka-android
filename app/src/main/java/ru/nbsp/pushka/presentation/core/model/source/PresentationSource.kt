package ru.nbsp.pushka.presentation.core.model.source

/**
 * Created by Dimorinny on 26.02.16.
 */
data class PresentationSource(
        val id: String,
        val params: List<PresentationParam>,
        val name: String,
        val description: String,
        val category: String
)