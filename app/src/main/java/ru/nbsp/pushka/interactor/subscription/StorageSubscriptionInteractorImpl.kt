package ru.nbsp.pushka.interactor.subscription

import ru.nbsp.pushka.data.DataManager
import ru.nbsp.pushka.mapper.data.subscription.DataSubscriptionMapper
import ru.nbsp.pushka.presentation.core.model.subscription.PresentationSubscription
import rx.Observable

/**
 * Created by Dimorinny on 05.04.16.
 */
class StorageSubscriptionInteractorImpl(
        val dataManager: DataManager,
        val dataSubscriptionMapper: DataSubscriptionMapper) : StorageSubscriptionInteractor {

    override fun saveSubscriptions(subscriptions: List<PresentationSubscription>): Observable<List<PresentationSubscription>> {
        dataManager.clearSubscriptions()
        dataManager.putSubscriptions(subscriptions.map { dataSubscriptionMapper.fromPresentationSubscription(it) })
        return Observable.just(subscriptions)
    }
}