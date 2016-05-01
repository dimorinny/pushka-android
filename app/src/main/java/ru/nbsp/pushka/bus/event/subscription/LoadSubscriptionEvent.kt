package ru.nbsp.pushka.bus.event.subscription

import ru.nbsp.pushka.bus.event.BaseEvent

/**
 * Created by Dimorinny on 30.04.16.
 */
sealed class LoadSubscriptionEvent : BaseEvent {
    class Success(val subscriptionId: String) : LoadSubscriptionEvent()
    class Error(val t: Throwable) : LoadSubscriptionEvent()
}