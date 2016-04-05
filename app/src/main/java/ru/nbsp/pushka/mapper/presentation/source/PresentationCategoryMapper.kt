package ru.nbsp.pushka.mapper.presentation.source

import ru.nbsp.pushka.data.model.source.DataCategory
import ru.nbsp.pushka.network.model.source.NetworkCategory
import ru.nbsp.pushka.presentation.core.model.source.PresentationCategory
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Dimorinny on 06.03.16.
 */
@Singleton
class PresentationCategoryMapper @Inject constructor() {
    fun fromDataCategory(dataCategory: DataCategory): PresentationCategory {
        return PresentationCategory(
                id = dataCategory.id,
                name = dataCategory.name,
                icon = dataCategory.icon,
                color = dataCategory.color)
    }

    fun fromNetworkCategory(networkCategory: NetworkCategory): PresentationCategory {
        return PresentationCategory(
                id = networkCategory.id,
                name = networkCategory.name,
                icon = networkCategory.icon,
                color = networkCategory.color)
    }
}