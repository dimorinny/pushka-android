package ru.nbsp.pushka.bus.event.source

/**
 * Created by Dimorinny on 11.03.16.
 */
sealed class LoadCategoriesEvent {
    class Success() : LoadCategoriesEvent()
    class Error(val t: Throwable) : LoadCategoriesEvent()
}