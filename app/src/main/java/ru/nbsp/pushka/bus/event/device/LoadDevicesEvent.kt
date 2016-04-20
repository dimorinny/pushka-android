package ru.nbsp.pushka.bus.event.device

/**
 * Created by Dimorinny on 20.04.16.
 */
sealed class LoadDevicesEvent {
    class Success() : LoadDevicesEvent()
    class Error(val t: Throwable) : LoadDevicesEvent()
}