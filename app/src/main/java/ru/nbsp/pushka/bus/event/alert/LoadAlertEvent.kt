package ru.nbsp.pushka.bus.event.alert

import ru.nbsp.pushka.bus.event.BaseEvent

/**
 * Created by Dimorinny on 26.03.16.
 */
sealed class LoadAlertEvent : BaseEvent {
    class Success(val alertId: String) : LoadAlertEvent()
    class Error(val t: Throwable) : LoadAlertEvent()
}