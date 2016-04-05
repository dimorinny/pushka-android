package ru.nbsp.pushka.repository.subscription

import ru.nbsp.pushka.mapper.presentation.subscription.PresentationSubscriptionMapper
import ru.nbsp.pushka.network.service.PushkaSubscriptionService
import ru.nbsp.pushka.presentation.core.model.subscription.PresentationSubscription
import ru.nbsp.pushka.util.SchedulersUtils
import rx.Observable
import java.util.*

/**
 * Created by Dimorinny on 14.03.16.
 */
class ApiSubscriptionRepository(
        val apiPushka: PushkaSubscriptionService,
        val subscriptionMapper: PresentationSubscriptionMapper,
        val schedulersUtils: SchedulersUtils) : SubscriptionRepository {

    override fun getSubscriptions(): Observable<List<PresentationSubscription>> {
        return apiPushka.getSubscriptions()
                .map {
                    var result = ArrayList<PresentationSubscription>()
                    for (subscription in it.subscriptions) {
                        result.add(subscriptionMapper.fromNetworkSubscription(subscription))
                    }
                    result
                }
                .compose(schedulersUtils.applySchedulers<List<PresentationSubscription>>())
    }
}