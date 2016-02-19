package ru.nbsp.pushka.iteractor.login.param

import java.io.Serializable

/**
 * Created by Dimorinny on 17.02.16.
 */
data class LoginParam(val provider: String, val token: String) : Serializable