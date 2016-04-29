package ru.nbsp.pushka.bus.event.source

import ru.nbsp.pushka.bus.event.BaseEvent

/**
 * Created by Dimorinny on 11.03.16.
 */
sealed class LoadCategoriesEvent : BaseEvent {
    class Success() : LoadCategoriesEvent()
    class Error(val t: Throwable) : LoadCategoriesEvent()
}