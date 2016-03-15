package ru.nbsp.pushka.presentation.core.model.alert

/**
 * Created by Dimorinny on 06.03.16.
 */
data class PresentationAlert(
        val id: String,
        val title: String,
        val text: String,
        val photo: String?,
        val sourceImage: String?,
        val sourceTitle: String?,
        val shareLink: String,
        val actions: List<PresentationAction>
)