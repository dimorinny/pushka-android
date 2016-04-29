package ru.nbsp.pushka.bus.event.device

import ru.nbsp.pushka.bus.event.BaseEvent

/**
 * Created by Dimorinny on 20.04.16.
 */
sealed class LoadDevicesEvent : BaseEvent {
    class Success() : LoadDevicesEvent()
    class Error(val t: Throwable) : LoadDevicesEvent()
}