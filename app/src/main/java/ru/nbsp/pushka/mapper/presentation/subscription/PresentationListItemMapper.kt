package ru.nbsp.pushka.mapper.presentation.subscription

import ru.nbsp.pushka.network.model.subscription.NetworkListItem
import ru.nbsp.pushka.presentation.core.model.subscription.PresentationListItem
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Dimorinny on 13.04.16.
 */
@Singleton
class PresentationListItemMapper @Inject constructor() {

    fun fromNetworkListItem(item: NetworkListItem): PresentationListItem {
        return PresentationListItem(
                name = item.name,
                value = item.value,
                description = item.description,
                image = item.image
        )
    }
}