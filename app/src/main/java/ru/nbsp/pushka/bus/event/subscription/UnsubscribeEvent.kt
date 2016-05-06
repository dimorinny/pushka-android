package ru.nbsp.pushka.bus.event.subscription

import ru.nbsp.pushka.bus.event.BaseEvent

/**
 * Created by Dimorinny on 06.05.16.
 */
sealed class UnsubscribeEvent : BaseEvent {
    class Success(val id: String) : UnsubscribeEvent()
    class Error(val t: Throwable) : UnsubscribeEvent()
}