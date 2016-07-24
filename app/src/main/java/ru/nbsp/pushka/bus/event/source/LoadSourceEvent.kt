package ru.nbsp.pushka.bus.event.source

import ru.nbsp.pushka.bus.event.BaseEvent

/**
 * Created by Dimorinny on 11.03.16.
 */
sealed class LoadSourceEvent : BaseEvent {
    class Success(val sourceId: String) : LoadSourceEvent()
    class Error(val t: Throwable) : LoadSourceEvent()
}