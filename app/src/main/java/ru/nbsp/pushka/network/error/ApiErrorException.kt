package ru.nbsp.pushka.network.error

/**
 * Created by Dimorinny on 20.04.16.
 */
class ApiErrorException(val code: Int, val description: String) : Throwable()