package ru.nbsp.pushka.mapper.presentation.subscription

import com.google.gson.Gson
import ru.nbsp.pushka.data.model.subscription.DataSubscription
import ru.nbsp.pushka.network.model.subscription.NetworkSubscription
import ru.nbsp.pushka.presentation.core.model.subscription.PresentationSubscription
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Dimorinny on 14.03.16.
 */
@Singleton
class PresentationSubscriptionMapper @Inject constructor(val gson: Gson) {

    @Suppress("UNCHECKED_CAST")
    fun fromNetworkSubscription(networkSubscription: NetworkSubscription): PresentationSubscription {
        return PresentationSubscription(
                id = networkSubscription.id,
                title = networkSubscription.title,
                sourceTitle = networkSubscription.sourceTitle,
                icon = networkSubscription.icon,
                color = networkSubscription.color,
                sourceId = networkSubscription.sourceId,
                notification = networkSubscription.notification,
                sound = networkSubscription.sound,
                values = networkSubscription.params)
    }

    @Suppress("UNCHECKED_CAST")
    fun fromDataSubscription(dataSubscription: DataSubscription): PresentationSubscription {
        return PresentationSubscription(
                id = dataSubscription.id,
                title = dataSubscription.title,
                sourceTitle = dataSubscription.sourceTitle,
                icon = dataSubscription.icon,
                color = dataSubscription.color,
                sourceId = dataSubscription.sourceId,
                notification = dataSubscription.notification,
                sound = dataSubscription.sound,
                values = gson.fromJson(dataSubscription.values, HashMap::class.java) as HashMap<String, String>)

    }
}