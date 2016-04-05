package ru.nbsp.pushka.mapper.data.source

import ru.nbsp.pushka.data.model.source.DataCategory
import ru.nbsp.pushka.network.model.source.NetworkCategory
import ru.nbsp.pushka.presentation.core.model.source.PresentationCategory
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Dimorinny on 11.03.16.
 */
@Singleton
class DataCategoryMapper @Inject constructor() {
    fun fromPresentationCategory(presentationCategory: PresentationCategory): DataCategory {
        return DataCategory(
                id = presentationCategory.id,
                name = presentationCategory.name,
                icon = presentationCategory.icon,
                color= presentationCategory.color)
    }

    fun fromNetworkCategory(networkCategory: NetworkCategory): DataCategory {
        return DataCategory(
                id = networkCategory.id,
                name = networkCategory.name,
                icon = networkCategory.icon,
                color = networkCategory.color)
    }
}