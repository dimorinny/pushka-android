package ru.nbsp.pushka.mapper.data.subscription

import ru.nbsp.pushka.data.model.subscription.DataSubscription
import ru.nbsp.pushka.network.model.subscription.NetworkSubscription
import ru.nbsp.pushka.presentation.core.model.subscription.PresentationSubscription
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Dimorinny on 05.04.16.
 */
@Singleton
class DataSubscriptionMapper @Inject constructor() {

    fun fromNetworkSubscription(networkSubscription: NetworkSubscription): DataSubscription {
        return DataSubscription(
                id = networkSubscription.id,
                title = networkSubscription.title,
                sourceTitle = networkSubscription.sourceTitle,
                icon = networkSubscription.icon,
                color = networkSubscription.color)
    }

    fun fromPresentationSubscription(presentationSubscription: PresentationSubscription): DataSubscription {
        return DataSubscription(
                id = presentationSubscription.id,
                title = presentationSubscription.title,
                sourceTitle = presentationSubscription.sourceTitle,
                icon = presentationSubscription.icon,
                color = presentationSubscription.color)
    }
}