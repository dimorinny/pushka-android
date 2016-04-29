package ru.nbsp.pushka.presentation.subscription.detail

import ru.nbsp.pushka.presentation.core.base.BaseView
import ru.nbsp.pushka.presentation.core.model.source.PresentationParam
import ru.nbsp.pushka.presentation.core.model.subscription.PresentationSubscription

/**
 * Created by Dimorinny on 29.04.16.
 */
interface SubscriptionView : BaseView {
    fun setParams(params: List<PresentationParam>)
    fun setSubscriptionData(subscription: PresentationSubscription)
    fun setTitle(sourceTitle: String)
    fun validateFields(): Boolean
    fun showUnsubscribeProgressDialog()
    fun hideUnsubscribeProgressDialog()
}