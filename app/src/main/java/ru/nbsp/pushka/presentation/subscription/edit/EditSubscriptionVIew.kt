package ru.nbsp.pushka.presentation.subscription.edit

import ru.nbsp.pushka.presentation.core.base.BaseView
import ru.nbsp.pushka.presentation.core.model.source.PresentationParam
import ru.nbsp.pushka.presentation.core.model.subscription.PresentationSubscription
import ru.nbsp.pushka.presentation.core.state.State

/**
 * Created by Dimorinny on 29.04.16.
 */
interface EditSubscriptionView : BaseView {
    fun setParams(params: List<PresentationParam>)
    fun setState(state: State)
    fun setSubscriptionData(subscription: PresentationSubscription)
    fun validateFields(): Boolean
    fun showUnsubscribeProgressDialog()
    fun hideUnsubscribeProgressDialog()
    fun showMessage(message: String)
    fun closeScreen()
}