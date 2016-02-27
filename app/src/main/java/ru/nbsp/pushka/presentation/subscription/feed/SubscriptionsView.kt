package ru.nbsp.pushka.presentation.subscription.feed

import ru.nbsp.pushka.network.model.subscription.Subscription
import ru.nbsp.pushka.presentation.core.base.BaseView

/**
 * Created by Dimorinny on 26.02.16.
 */
interface SubscriptionsView : BaseView {
    fun setSubscriptions(subscriptions: List<Subscription>)
}