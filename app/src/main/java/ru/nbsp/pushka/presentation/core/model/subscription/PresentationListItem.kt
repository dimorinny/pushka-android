package ru.nbsp.pushka.presentation.core.model.subscription

/**
 * Created by Dimorinny on 13.04.16.
 */
data class PresentationListItem(
        val name: String,
        val value: String,
        val description: String?,
        val image: String?) {

    override fun toString(): String = name
}