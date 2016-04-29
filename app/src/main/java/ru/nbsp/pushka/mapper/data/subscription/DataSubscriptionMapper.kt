package ru.nbsp.pushka.mapper.data.subscription

import android.util.Log
import com.google.gson.Gson
import ru.nbsp.pushka.data.model.subscription.DataSubscription
import ru.nbsp.pushka.network.model.subscription.NetworkSubscription
import ru.nbsp.pushka.presentation.core.model.subscription.PresentationSubscription
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Dimorinny on 05.04.16.
 */
@Singleton
class DataSubscriptionMapper @Inject constructor(val gson: Gson) {

    fun fromNetworkSubscription(networkSubscription: NetworkSubscription): DataSubscription {
        return DataSubscription(
                id = networkSubscription.id,
                title = networkSubscription.title,
                sourceTitle = networkSubscription.sourceTitle,
                icon = networkSubscription.icon,
                color = networkSubscription.color,
                sourceId = networkSubscription.sourceId,
                values = gson.toJson(networkSubscription.params))
    }

    fun fromPresentationSubscription(presentationSubscription: PresentationSubscription): DataSubscription {
        Log.d("fromPresentationSubscription", presentationSubscription.values.toString())
        return DataSubscription(
                id = presentationSubscription.id,
                title = presentationSubscription.title,
                sourceTitle = presentationSubscription.sourceTitle,
                icon = presentationSubscription.icon,
                color = presentationSubscription.color,
                sourceId = presentationSubscription.sourceId,
                values = gson.toJson(presentationSubscription.values))
    }
}