package ru.nbsp.pushka.presentation.subscription.feed

import ru.nbsp.pushka.presentation.core.base.BaseView
import ru.nbsp.pushka.presentation.core.model.subscription.PresentationSubscription
import ru.nbsp.pushka.presentation.core.state.State

/**
 * Created by Dimorinny on 26.02.16.
 */
interface SubscriptionsView : BaseView {
    fun setSubscriptions(subscriptions: List<PresentationSubscription>)
    fun setState(state: State)
    fun disableSwipeRefresh()
    fun openSubscriptionScreen(subscription: PresentationSubscription)
}