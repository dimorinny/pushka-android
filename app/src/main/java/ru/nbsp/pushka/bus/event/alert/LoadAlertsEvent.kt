package ru.nbsp.pushka.bus.event.alert

import ru.nbsp.pushka.bus.event.BaseEvent

/**
 * Created by Dimorinny on 02.03.16.
 */
sealed class LoadAlertsEvent : BaseEvent {
    class Success() : LoadAlertsEvent()
    class Error(val t: Throwable) : LoadAlertsEvent()
}