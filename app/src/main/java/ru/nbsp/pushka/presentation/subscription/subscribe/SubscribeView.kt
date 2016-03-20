package ru.nbsp.pushka.presentation.subscription.subscribe

import ru.nbsp.pushka.presentation.core.base.BaseView
import ru.nbsp.pushka.presentation.core.model.source.PresentationParam
import ru.nbsp.pushka.presentation.core.model.source.PresentationSource

/**
 * Created by egor on 18.03.16.
 */
interface SubscribeView: BaseView {
    fun setParams(params: List<PresentationParam>)

    fun getParamsMap(): Map<String, String?>

    fun validateFields(): Boolean


}