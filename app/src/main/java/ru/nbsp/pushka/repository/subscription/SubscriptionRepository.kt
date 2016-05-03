package ru.nbsp.pushka.repository.subscription

import ru.nbsp.pushka.presentation.core.model.subscription.PresentationSubscription
import rx.Observable

/**
 * Created by Dimorinny on 26.02.16.
 */
interface SubscriptionRepository {
    fun getSubscriptions(): Observable<List<PresentationSubscription>>
    fun getSubscriptionsWithFilter(query: String): Observable<List<PresentationSubscription>>
    fun getSubscription(subscriptionId: String): Observable<PresentationSubscription>
}