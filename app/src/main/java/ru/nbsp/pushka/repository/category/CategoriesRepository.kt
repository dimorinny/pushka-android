package ru.nbsp.pushka.repository.category

import ru.nbsp.pushka.presentation.core.model.source.PresentationCategory
import rx.Observable

/**
 * Created by Dimorinny on 08.03.16.
 */
interface CategoriesRepository {
    fun getCategories(): Observable<List<PresentationCategory>>
}