package ru.nbsp.pushka.repository.category

import ru.nbsp.pushka.data.DataManager
import ru.nbsp.pushka.mapper.presentation.source.PresentationCategoryMapper
import ru.nbsp.pushka.presentation.core.model.source.PresentationCategory
import rx.Observable
import java.util.*

/**
 * Created by Dimorinny on 11.03.16.
 */
class StorageCategoriesRepository(
        val dataManager: DataManager,
        val categoryMapper: PresentationCategoryMapper) : CategoriesRepository {

    override fun getCategories(): Observable<List<PresentationCategory>> {

        return dataManager.getCategoriesObservable()
                .map {
                    var result = ArrayList<PresentationCategory>()
                    for (category in it) {
                        result.add(categoryMapper.fromDataCategory(category))
                    }
                    result
                }
    }
}