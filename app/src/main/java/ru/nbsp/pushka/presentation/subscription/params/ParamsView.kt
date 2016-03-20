package ru.nbsp.pushka.presentation.subscription.params

import ru.nbsp.pushka.presentation.core.base.BaseView
import ru.nbsp.pushka.presentation.core.model.source.PresentationControl
import ru.nbsp.pushka.presentation.core.model.source.PresentationParam

/**
 * Created by egor on 17.03.16.
 */
interface ParamsView : BaseView {

    fun addParam(param: PresentationParam)

    fun getValue(param: PresentationParam): String?

    fun setError(param: PresentationParam)
    fun setNoError(param: PresentationParam)
}
