package ru.nbsp.pushka.presentation.core.model.subscription

/**
 * Created by Dimorinny on 26.02.16.
 */
data class PresentationSubscription(
        val id: String,
        val title: String,
        val sourceId: String,
        val sourceTitle: String,
        val icon: String,
        val color: String,
        val values: Map<String, String>
)