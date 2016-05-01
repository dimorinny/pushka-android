package ru.nbsp.pushka.interactor.source

import ru.nbsp.pushka.data.DataManager
import ru.nbsp.pushka.mapper.data.source.DataSourceMapper
import ru.nbsp.pushka.presentation.core.model.source.PresentationSource
import rx.Observable

/**
 * Created by Dimorinny on 07.03.16.
 */
class StorageSourceInteractorImpl(val dataManager: DataManager,
                                  val dataSourceMapper: DataSourceMapper) : StorageSourceInteractor {

    override fun saveSources(sources: List<PresentationSource>, categoryId: String): Observable<List<PresentationSource>> {
        dataManager.clearSources(categoryId)
        dataManager.putSources(sources.map { dataSourceMapper.fromPresentationSource(it) })
        return Observable.just(sources)
    }

    override fun saveSource(source: PresentationSource): Observable<PresentationSource> {
        dataManager.clearSource(source.id)
        dataManager.putSource(dataSourceMapper.fromPresentationSource(source))
        return Observable.just(source)
    }
}