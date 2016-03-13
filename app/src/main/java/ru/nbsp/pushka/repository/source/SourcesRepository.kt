package ru.nbsp.pushka.repository.source

import ru.nbsp.pushka.presentation.core.model.source.PresentationSource
import rx.Observable


/**
 * Created by Dimorinny on 26.02.16.
 */
interface SourcesRepository {
    fun getSources(categoryId: String): Observable<List<PresentationSource>>
}