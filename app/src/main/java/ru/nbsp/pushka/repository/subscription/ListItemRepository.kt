package ru.nbsp.pushka.repository.subscription

import ru.nbsp.pushka.presentation.core.model.subscription.PresentationListItem
import rx.Observable

/**
 * Created by Dimorinny on 13.04.16.
 */
interface ListItemRepository {
    fun getListItems(sourceId: String, listId: String, query: String): Observable<List<PresentationListItem>>
}