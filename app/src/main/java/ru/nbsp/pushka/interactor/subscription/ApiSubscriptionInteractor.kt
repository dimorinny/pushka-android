package ru.nbsp.pushka.interactor.subscription

import ru.nbsp.pushka.network.request.SubscribeRequest
import ru.nbsp.pushka.presentation.core.model.subscription.PresentationSubscription
import rx.Observable

/**
 * Created by Dimorinny on 27.02.16.
 */
interface ApiSubscriptionInteractor {
    fun subscribe(subscribeRequest: SubscribeRequest): Observable<PresentationSubscription>
}