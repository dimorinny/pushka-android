package ru.nbsp.pushka.presentation.subscription.subscribe

import ru.nbsp.pushka.presentation.core.base.BaseView
import ru.nbsp.pushka.presentation.core.model.source.PresentationParam
import ru.nbsp.pushka.presentation.core.model.source.PresentationSource
import ru.nbsp.pushka.presentation.core.state.State

/**
 * Created by egor on 18.03.16.
 */
interface SubscribeView: BaseView {
    fun closeScreen()
    fun setParams(params: List<PresentationParam>)
    fun setSourceData(source: PresentationSource)
    fun validateFields(): Boolean
    fun showShareMenu(subject: String, text: String)
    fun showSubscribeProgressDialog()
    fun hideSubscribeProgressDialog()
    fun showMessage(message: String)
    fun showError(message: String)
    fun setState(state: State)
    fun showSubscribeConnectionError(message: String)
}