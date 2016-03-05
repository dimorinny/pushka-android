package ru.nbsp.pushka.repository.source

import ru.nbsp.pushka.data.model.source.Source
import ru.nbsp.pushka.network.service.PushkaSourceService
import ru.nbsp.pushka.util.SchedulersUtils
import rx.Observable

/**
 * Created by Dimorinny on 27.02.16.
 */
class ServerSourcesRepository(
        val apiPushka: PushkaSourceService,
        val schedulersUtils: SchedulersUtils) : SourcesRepository {

    override fun getSources(): Observable<List<Source>> {
        return apiPushka
                .getSources()
                .map { it.sources }
                .compose(schedulersUtils.applySchedulers<List<Source>>())
    }
}