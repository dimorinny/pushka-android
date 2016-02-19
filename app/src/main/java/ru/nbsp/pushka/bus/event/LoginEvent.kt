package ru.nbsp.pushka.bus.event

import ru.nbsp.pushka.api.response.LoginResponse

/**
 * Created by Dimorinny on 19.02.16.
 */

sealed class LoginEvent {
    class Response(val response: LoginResponse) : LoginEvent()
    class Error(val t: Throwable) : LoginEvent()
}