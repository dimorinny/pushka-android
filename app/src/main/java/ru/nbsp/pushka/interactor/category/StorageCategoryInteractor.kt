package ru.nbsp.pushka.interactor.category

import ru.nbsp.pushka.presentation.core.model.source.PresentationCategory
import rx.Observable

/**
 * Created by Dimorinny on 11.03.16.
 */
interface StorageCategoryInteractor {
    fun saveCategories(categories: List<PresentationCategory>): Observable<List<PresentationCategory>>
}