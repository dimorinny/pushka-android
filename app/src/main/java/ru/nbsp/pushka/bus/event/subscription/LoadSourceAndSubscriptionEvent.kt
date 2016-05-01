package ru.nbsp.pushka.bus.event.subscription

import ru.nbsp.pushka.bus.event.BaseEvent

/**
 * Created by Dimorinny on 01.05.16.
 */
sealed class LoadSourceAndSubscriptionEvent : BaseEvent {
    class Success(val sourceId: String, val subscriptionId: String) : LoadSourceAndSubscriptionEvent()
    class Error(val t: Throwable) : LoadSourceAndSubscriptionEvent()
}