package ru.nbsp.pushka.bus.event.subscription

/**
 * Created by Dimorinny on 05.04.16.
 */
sealed class LoadSubscriptionsEvent {
    class Success() : LoadSubscriptionsEvent()
    class Error(val t: Throwable) : LoadSubscriptionsEvent()
}