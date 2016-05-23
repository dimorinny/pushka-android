package ru.nbsp.pushka.bus.event.device

import ru.nbsp.pushka.bus.event.BaseEvent

/**
 * Created by Dimorinny on 23.05.16.
 */
sealed class RemoveGcmDeviceEvent : BaseEvent {
    class Success() : RemoveGcmDeviceEvent()
    class Error(val t: Throwable) : RemoveGcmDeviceEvent()
}