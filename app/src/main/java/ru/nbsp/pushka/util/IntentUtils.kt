package ru.nbsp.pushka.util

import android.content.Intent
import android.os.Bundle
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Dimorinny on 24.02.16.
 */
@Singleton
class IntentUtils @Inject constructor() {

    fun intentToFragmentArguments(fragmentArguments: Bundle?, intent: Intent?): Bundle {
        var arguments = fragmentArguments

        if (arguments == null) {
            arguments = Bundle() // создаем новый объект типа Bundle
        }

        if (intent == null) {
            return arguments
        }

        val data = intent.data
        if (data != null) {
            arguments.putParcelable("_uri", data)
        }

        val extras = intent.extras
        if (extras != null) {
            arguments.putAll(intent.extras)
        }

        return arguments
    }
}