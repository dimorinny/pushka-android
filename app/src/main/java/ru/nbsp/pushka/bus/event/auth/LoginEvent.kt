package ru.nbsp.pushka.bus.event.auth

/**
 * Created by Dimorinny on 19.02.16.
 */

sealed class LoginEvent {
    class Success() : LoginEvent()
    class Error(val t: Throwable) : LoginEvent()
}