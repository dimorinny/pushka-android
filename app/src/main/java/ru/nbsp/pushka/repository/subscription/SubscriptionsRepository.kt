package ru.nbsp.pushka.repository.subscription

import ru.nbsp.pushka.network.model.subscription.Subscription
import rx.Observable

/**
 * Created by Dimorinny on 26.02.16.
 */
interface SubscriptionsRepository {
    fun getSubscriptions(): Observable<List<Subscription>>
}