package ru.nbsp.pushka.util

import android.content.Context
import ru.nbsp.pushka.R
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Dimorinny on 31.05.16.
 */
@Singleton
class AuthBackendUtils @Inject constructor(val context: Context) {

    companion object {
        private val PREFIX_MAP = mapOf(
                "vk" to R.string.vk,
                "google" to R.string.google
        )
    }

    fun backendNameById(identity: String): String? {
        for ((prefix, name) in PREFIX_MAP) {
            if (identity.startsWith(prefix)) {
                return context.getString(name)
            }
        }

        return null
    }
}