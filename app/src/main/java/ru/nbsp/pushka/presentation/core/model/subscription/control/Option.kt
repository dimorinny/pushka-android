package ru.nbsp.pushka.presentation.core.model.subscription.control

/**
 * Created by Dimorinny on 09.04.16.
 */
data class Option(val name: String, val value: String?) {
    override fun toString(): String = name
}