package ru.nbsp.pushka.interactor.subscription

import ru.nbsp.pushka.presentation.core.model.subscription.PresentationSubscription
import rx.Observable

/**
 * Created by Dimorinny on 05.04.16.
 */
interface StorageSubscriptionInteractor {
    fun saveSubscriptions(subscriptions: List<PresentationSubscription>): Observable<List<PresentationSubscription>>
    fun saveSubscription(subscription: PresentationSubscription): Observable<PresentationSubscription>
}