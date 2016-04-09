package ru.nbsp.pushka.presentation.subscription.params.control

/**
 * Created by egor on 16.03.16.
 */
interface Control {
    interface OnChangeListener {
        fun onChange(newValue: String?)
    }

    fun getValue(): String?
    fun setOnChangeListener(onChangeListener: OnChangeListener)
    fun setError()
    fun setNoError()
}
