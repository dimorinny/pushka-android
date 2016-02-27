package ru.nbsp.pushka.repository.source

import ru.nbsp.pushka.network.model.source.Source
import rx.Observable


/**
 * Created by Dimorinny on 26.02.16.
 */
interface SourcesRepository {
    fun getSources(): Observable<List<Source>>
}