package ru.nbsp.pushka.util

import android.content.Context
import android.widget.Toast
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Dimorinny on 20.02.16.
 */

@Singleton
class AlertUtils @Inject constructor() {
    fun showAlert(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}