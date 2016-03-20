package ru.nbsp.pushka.repository.subscription

import ru.nbsp.pushka.presentation.core.model.source.control.SimpleListControlItem
import ru.nbsp.pushka.presentation.core.model.subscription.PresentationSubscription
import rx.Observable

/**
 * Created by Dimorinny on 26.02.16.
 */
interface SimpleListRepository {
    fun getList(query: String?): Observable<List<SimpleListControlItem>>
}