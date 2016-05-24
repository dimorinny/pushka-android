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
    }

    private val errorMessages = mapOf(
            Pair(CONNECTION_ERROR_CODE, R.string.error_connection),
            Pair(ALREADY_SUBSCRIBED_CODE, R.string.error_already_subscribed),
            Pair(SOURCE_NOT_FOUND_CODE, R.string.error_source_not_found),
            Pair(SUBSCRIPTION_NOT_FOUND_CODE, R.string.error_subscription_not_found)
    )

    fun errorMessage(code: Int): String {
        return context.getString(errorMessages[code]!!)
    }
}