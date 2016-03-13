package ru.nbsp.pushka.mapper.presentation.subscription

import ru.nbsp.pushka.network.model.subscription.NetworkSubscription
import ru.nbsp.pushka.presentation.core.model.subscription.PresentationSubscription
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Dimorinny on 14.03.16.
 */
@Singleton
class PresentationSubscriptionMapper @Inject constructor() {

    fun fromNetworkSubscription(networkSubscription: NetworkSubscription): PresentationSubscription {
        // TODO: fix fake presentation subscription
        return PresentationSubscription(networkSubscription.title, networkSubscription.title)
    }
}