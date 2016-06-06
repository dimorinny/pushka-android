package ru.nbsp.pushka.network

import ru.nbsp.pushka.BuildConfig

/**
 * Created by Dimorinny on 07.06.16.
 */
class ApiConfig {
    companion object {
        const val TOKEN_HEADER = "Token"
        const val ERROR_CODE_KEY = "error"
        const val ERROR_MESSAGE_KEY = "description"

        const val VERSION = "v1"
        val BASE_URL = if (BuildConfig.DEBUG) "https://dev.pushka.xyz/api/$VERSION/" else "https://pushka.xyz/api/$VERSION/"
    }
}