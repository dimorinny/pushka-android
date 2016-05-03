package ru.nbsp.pushka.repository.subscription

import ru.nbsp.pushka.data.DataManager
import ru.nbsp.pushka.mapper.presentation.subscription.PresentationSubscriptionMapper
import ru.nbsp.pushka.presentation.core.model.subscription.PresentationSubscription
import rx.Observable
import java.util.*

/**
 * Created by Dimorinny on 05.04.16.
 */
class StorageSubscriptionRepository(
        val dataManager: DataManager,
        val subscriptionMapper: PresentationSubscriptionMapper) : SubscriptionRepository {

    override fun getSubscriptions(): Observable<List<PresentationSubscription>> {
        return dataManager.getSubscriptionsObservable()
                .map {
                    var result = ArrayList<PresentationSubscription>()
                    for (alert in it) {
                        result.add(subscriptionMapper.fromDataSubscription(alert))
                    }
                    result
                }
    }

    override fun getSubscriptionsWithFilter(query: String): Observable<List<PresentationSubscription>> {
        return dataManager.getSubscriptionsWithFilterObservable(query)
                .map {
                    var result = ArrayList<PresentationSubscription>()
                    for (alert in it) {
                        result.add(subscriptionMapper.fromDataSubscription(alert))
                    }
                    result
                }
    }

    override fun getSubscription(subscriptionId: String): Observable<PresentationSubscription> {
        return dataManager.getSubscriptionObservable(subscriptionId)
                .map { subscriptionMapper.fromDataSubscription(it) }
    }
}