package ru.nbsp.pushka.presentation.core.model.source.control

/**
 * Created by egor on 17.03.16.
 */
data class DropdownAttributes(val options: List<Option>) {
    data class Option (
        val name: String,
        val value: String
    )
}
