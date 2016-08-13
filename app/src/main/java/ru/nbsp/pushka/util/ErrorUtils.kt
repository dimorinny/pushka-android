package ru.nbsp.pushka.util

import android.content.Context
import ru.nbsp.pushka.R
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Dimorinny on 24.05.16.
 */
@Singleton
class ErrorUtils
    @Inject constructor(val context: Context) {

    companion object {
        const val CONNECTION_ERROR_CODE = 228
        const val ALREADY_SUBSCRIBED_CODE = 450
        const val SOURCE_NOT_FOUND_CODE = 439
        const val SUBSCRIPTION_NOT_FOUND_CODE = 451
        const val INTERNAL_SERVER_ERROR_CODE = 500
        const val AUTH_ERROR_CODE = 401
    }

    private val errorMessages = mapOf(
            CONNECTION_ERROR_CODE to R.string.error_connection,
            ALREADY_SUBSCRIBED_CODE to R.string.error_already_subscribed,
            SOURCE_NOT_FOUND_CODE to R.string.error_source_not_found,
            SUBSCRIPTION_NOT_FOUND_CODE to R.string.error_subscription_not_found,
            INTERNAL_SERVER_ERROR_CODE to R.string.error_something_wrong
    )

    fun errorMessage(code: Int): String {
        return context.getString(errorMessages[code]!!)
    }
}