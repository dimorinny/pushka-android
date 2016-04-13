package ru.nbsp.pushka.repository.subscription

import ru.nbsp.pushka.mapper.presentation.subscription.PresentationListItemMapper
import ru.nbsp.pushka.network.service.PushkaSubscriptionService
import ru.nbsp.pushka.presentation.core.model.subscription.PresentationListItem
import ru.nbsp.pushka.util.SchedulersUtils
import rx.Observable
import java.util.*

/**
 * Created by Dimorinny on 13.04.16.
 */
class ApiListItemRepository(
        val apiPushka: PushkaSubscriptionService,
        val presentationListMapper: PresentationListItemMapper,
        val schedulersUtils: SchedulersUtils) : ListItemRepository {

    override fun getListItems(sourceId: String, listId: String, query: String): Observable<List<PresentationListItem>> {
        return apiPushka.getListItems(sourceId, listId, query)
                .map {
                    var result = ArrayList<PresentationListItem>()
                    for (alert in it.list) {
                        result.add(presentationListMapper.fromNetworkListItem(alert))
                    }
                    result
                }
                .compose(schedulersUtils.applySchedulers<List<PresentationListItem>>())
    }
}