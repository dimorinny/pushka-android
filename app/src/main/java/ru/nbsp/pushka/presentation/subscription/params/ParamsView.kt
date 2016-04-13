package ru.nbsp.pushka.presentation.subscription.params

import ru.nbsp.pushka.presentation.core.base.BaseView
import ru.nbsp.pushka.presentation.core.model.source.PresentationParam

/**
 * Created by egor on 17.03.16.
 */
interface ParamsView : BaseView {
    fun addParam(param: PresentationParam)
    fun showParams()
    fun hideParams()
    fun getValue(name: String): String?
    fun setValue(name: String, value: String?)
    fun setError(name: String)
    fun setNoError(name: String)
}
