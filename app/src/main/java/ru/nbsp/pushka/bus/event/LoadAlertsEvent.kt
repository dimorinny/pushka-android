package ru.nbsp.pushka.bus.event

/**
 * Created by Dimorinny on 02.03.16.
 */
sealed class LoadAlertsEvent {
    class Success() : LoadAlertsEvent()
    class Error(val t: Throwable) : LoadAlertsEvent()
}