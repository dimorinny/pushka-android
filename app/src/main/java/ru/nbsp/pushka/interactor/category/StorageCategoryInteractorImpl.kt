package ru.nbsp.pushka.interactor.category

import ru.nbsp.pushka.data.DataManager
import ru.nbsp.pushka.mapper.data.source.DataCategoryMapper
import ru.nbsp.pushka.presentation.core.model.source.PresentationCategory
import rx.Observable

/**
 * Created by Dimorinny on 11.03.16.
 */
class StorageCategoryInteractorImpl(val dataManager: DataManager,
                                    val dataCategoryMapper: DataCategoryMapper) : StorageCategoryInteractor {

    override fun saveCategories(categories: List<PresentationCategory>): Observable<List<PresentationCategory>> {
        dataManager.clearCategories()
        dataManager.putCategories(categories.map { dataCategoryMapper.fromPresentationCategory(it) })
        return Observable.just(categories)
    }
}