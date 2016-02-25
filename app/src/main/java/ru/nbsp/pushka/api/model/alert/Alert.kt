package ru.nbsp.pushka.api.model.alert

/**
 * Created by Dimorinny on 25.02.16.
 */
data class Alert(
        val title: String,
        val text: String,
        val photo: String?,
        val sourceImage: String,
        val sourceTitle: String,
        val shareLink: String
)