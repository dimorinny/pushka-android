package ru.nbsp.pushka.interactor.source

import ru.nbsp.pushka.data.DataManager
import ru.nbsp.pushka.presentation.core.model.source.PresentationSource
import rx.Observable

/**
 * Created by Dimorinny on 07.03.16.
 */
class StorageSourceInteractorImpl(val dataManager: DataManager) : StorageSourceInteractor {

    override fun saveSources(sources: List<PresentationSource>): Observable<List<PresentationSource>> {
        dataManager.clearSources()
        // TODO: put
        return Observable.just(sources)
    }
}