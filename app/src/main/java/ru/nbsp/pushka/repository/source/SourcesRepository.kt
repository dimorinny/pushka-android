package ru.nbsp.pushka.repository.source

import ru.nbsp.pushka.data.model.source.Source
import rx.Observable


/**
 * Created by Dimorinny on 26.02.16.
 */
interface SourcesRepository {
    fun getSources(): Observable<List<Source>>
}