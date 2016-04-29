package ru.nbsp.pushka.bus.event.subscription

import ru.nbsp.pushka.bus.event.BaseEvent

/**
 * Created by Dimorinny on 27.04.16.
 */
sealed class SubscribeEvent : BaseEvent {
    class Success() : SubscribeEvent()
    class Error(val t: Throwable) : SubscribeEvent()
}