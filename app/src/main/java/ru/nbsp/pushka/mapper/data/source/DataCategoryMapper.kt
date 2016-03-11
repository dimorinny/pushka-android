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
        return DataCategory(presentationCategory.id, presentationCategory.name, presentationCategory.image)
    }

    fun fromNetworkCategory(networkCategory: NetworkCategory): DataCategory {
        return DataCategory(networkCategory.id, networkCategory.name, networkCategory.image)
    }
}