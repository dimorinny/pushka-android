package ru.nbsp.pushka.interactor.source

import ru.nbsp.pushka.presentation.core.model.source.PresentationSource
import rx.Observable

/**
 * Created by Dimorinny on 07.03.16.
 */
interface StorageSourceInteractor {
    fun saveSources(sources: List<PresentationSource>): Observable<List<PresentationSource>>
}