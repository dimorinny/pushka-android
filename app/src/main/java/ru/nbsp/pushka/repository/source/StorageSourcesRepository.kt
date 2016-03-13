package ru.nbsp.pushka.repository.source

import ru.nbsp.pushka.data.DataManager
import ru.nbsp.pushka.mapper.presentation.source.PresentationSourceMapper
import ru.nbsp.pushka.presentation.core.model.source.PresentationSource
import rx.Observable
import java.util.*

/**
 * Created by Dimorinny on 07.03.16.
 */
class StorageSourcesRepository(
        val dataManager: DataManager,
        val sourceMapper: PresentationSourceMapper) : SourcesRepository {

    override fun getSources(categoryId: String): Observable<List<PresentationSource>> {
        return dataManager.getSourcesObservable(categoryId)
                .map {
                    var result = ArrayList<PresentationSource>()
                    for (source in it) {
                        result.add(sourceMapper.fromDataSource(source))
                    }
                    result
                }
    }
}