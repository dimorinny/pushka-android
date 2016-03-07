package ru.nbsp.pushka.presentation.subscription.feed

import ru.nbsp.pushka.presentation.core.base.BaseView
import ru.nbsp.pushka.presentation.core.model.subscription.PresentationSubscription

/**
 * Created by Dimorinny on 26.02.16.
 */
interface SubscriptionsView : BaseView {
    fun setSubscriptions(subscriptions: List<PresentationSubscription>)
}