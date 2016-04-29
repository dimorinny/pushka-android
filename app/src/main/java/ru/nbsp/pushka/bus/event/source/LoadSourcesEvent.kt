package ru.nbsp.pushka.bus.event.source

import ru.nbsp.pushka.bus.event.BaseEvent

/**
 * Created by Dimorinny on 13.03.16.
 */
sealed class LoadSourcesEvent : BaseEvent {
    class Success() : LoadSourcesEvent()
    class Error(val t: Throwable) : LoadSourcesEvent()
}