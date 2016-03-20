package ru.nbsp.pushka.presentation.subscription.params.control

import android.content.Context
import android.view.View

/**
 * Created by egor on 16.03.16.
 */
interface Control {
    interface Callback {
        fun onChange(newValue: String?)
    }

    fun getValue(): String?

    fun setOnChangeCallback(callback: Callback)

    fun setError()
    fun setNoError()
}
