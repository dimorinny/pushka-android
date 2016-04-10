package ru.nbsp.pushka.repository.source

import ru.nbsp.pushka.mapper.presentation.source.PresentationSourceMapper
import ru.nbsp.pushka.network.service.PushkaSourceService
import ru.nbsp.pushka.presentation.core.model.source.PresentationSource
import ru.nbsp.pushka.util.SchedulersUtils
import rx.Observable
import java.util.*

/**
 * Created by Dimorinny on 27.02.16.
 */
class ServerSourcesRepository(
        val apiPushka: PushkaSourceService,
        val sourceMapper: PresentationSourceMapper,
        val schedulersUtils: SchedulersUtils) : SourcesRepository {

    override fun getSources(categoryId: String): Observable<List<PresentationSource>> {
        return apiPushka
                .getSources(categoryId)
                .map {
                    var result = ArrayList<PresentationSource>()
                    for (source in it.sources) {
                        result.add(sourceMapper.fromNetworkSource(source))
                    }
                    result
                }
                .compose(schedulersUtils.applySchedulers<List<PresentationSource>>())
    }

    override fun getSource(sourceId: String): Observable<PresentationSource> {
        throw UnsupportedOperationException()
    }
}