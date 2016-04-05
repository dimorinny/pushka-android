package ru.nbsp.pushka.mapper.presentation.subscription

import ru.nbsp.pushka.data.model.subscription.DataSubscription
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
        return PresentationSubscription(
                id = networkSubscription.id,
                title = networkSubscription.title,
                sourceTitle = networkSubscription.sourceTitle,
                icon = networkSubscription.icon,
                color = networkSubscription.color)
    }

    fun fromDataSubscription(dataSubscription: DataSubscription): PresentationSubscription {
        return PresentationSubscription(
                id = dataSubscription.id,
                title = dataSubscription.title,
                sourceTitle = dataSubscription.sourceTitle,
                icon = dataSubscription.icon,
                color = dataSubscription.color)
    }
}