package ru.nbsp.pushka.repository.list

import ru.nbsp.pushka.presentation.core.model.source.control.SimpleListControlItem
import rx.Observable

/**
 * Created by Dimorinny on 26.02.16.
 */
interface SimpleListRepository {
    fun getList(query: String?): Observable<List<SimpleListControlItem>>
}