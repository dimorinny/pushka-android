package ru.nbsp.pushka.network

/**
 * Created by Dimorinny on 07.06.16.
 */
class ApiConfig {
    companion object {
        const val TOKEN_HEADER = "Token"
        const val ERROR_CODE_KEY = "error"
        const val ERROR_MESSAGE_KEY = "description"

        const val VERSION = "v1"
        const val DEV_URL = "https://dev.pushka.xyz/api/$VERSION/"
        const val PROD_URL = "https://pushka.xyz/api/$VERSION/"

        const val DEVICE_ID = "134C8B7A80A1936D85748EF141D344C4"
    }
}