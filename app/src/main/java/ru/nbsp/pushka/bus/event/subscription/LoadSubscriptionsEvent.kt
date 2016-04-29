package ru.nbsp.pushka.bus.event.subscription

import ru.nbsp.pushka.bus.event.BaseEvent

/**
 * Created by Dimorinny on 05.04.16.
 */
sealed class LoadSubscriptionsEvent : BaseEvent {
    class Success() : LoadSubscriptionsEvent()
    class Error(val t: Throwable) : LoadSubscriptionsEvent()
}