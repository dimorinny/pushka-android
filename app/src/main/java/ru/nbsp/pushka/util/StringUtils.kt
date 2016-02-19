package ru.nbsp.pushka.util

import android.content.Context
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Dimorinny on 19.02.16.
 */
@Singleton
class StringUtils @Inject constructor(val context: Context) {

    fun getString(id: Int): String {
        return context.resources.getString(id)
    }
}