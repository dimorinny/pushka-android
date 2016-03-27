package ru.nbsp.pushka.bus.event

/**
 * Created by Dimorinny on 26.03.16.
 */
sealed class LoadAlertEvent {
    class Success(val alertId: String) : LoadAlertEvent()
    class Error(val t: Throwable) : LoadAlertEvent()
}