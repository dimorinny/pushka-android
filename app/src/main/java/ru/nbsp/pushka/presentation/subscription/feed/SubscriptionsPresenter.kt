package ru.nbsp.pushka.presentation.subscription.feed

import ru.nbsp.pushka.network.model.subscription.Subscription
import ru.nbsp.pushka.presentation.core.base.BasePresenter
import ru.nbsp.pushka.repository.subscription.SubscriptionsRepository
import rx.Subscriber
import javax.inject.Inject

/**
 * Created by Dimorinny on 26.02.16.
 */
class SubscriptionsPresenter
    @Inject constructor(val subscriptionsRepository: SubscriptionsRepository) : BasePresenter {

    override var view: SubscriptionsView? = null

    fun loadSubscriptionsFromCache() {
        subscriptionsRepository.getSubscriptions().subscribe(LoadSubscriptionsSubscriber())
    }

    inner class LoadSubscriptionsSubscriber : Subscriber<List<Subscription>>() {
        override fun onCompleted() {}

        override fun onError(t: Throwable) {
            t.printStackTrace()
        }

        override fun onNext(subscriptions: List<Subscription>) {
            view?.setSubscriptions(subscriptions)
        }
    }
}