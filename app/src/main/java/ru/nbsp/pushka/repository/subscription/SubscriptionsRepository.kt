package ru.nbsp.pushka.repository.subscription

import ru.nbsp.pushka.presentation.core.model.subscription.PresentationSubscription
import rx.Observable

/**
 * Created by Dimorinny on 26.02.16.
 */
interface SubscriptionsRepository {
    fun getSubscriptions(detail: Boolean): Observable<List<PresentationSubscription>>
}