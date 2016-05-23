package ru.nbsp.pushka.bus.event.auth

import ru.nbsp.pushka.bus.event.BaseEvent

/**
 * Created by Dimorinny on 19.02.16.
 */
sealed class LoginEvent : BaseEvent {
    class Success() : LoginEvent()
    class Error(val t: Throwable) : LoginEvent()
}