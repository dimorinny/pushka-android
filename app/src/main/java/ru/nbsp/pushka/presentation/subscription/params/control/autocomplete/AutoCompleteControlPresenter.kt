package ru.nbsp.pushka.presentation.subscription.params.control.autocomplete

import ru.nbsp.pushka.di.annotation.ApiRepository
import ru.nbsp.pushka.presentation.core.base.BasePresenter
import ru.nbsp.pushka.presentation.core.model.subscription.control.ListAttributes
import ru.nbsp.pushka.presentation.core.model.subscription.PresentationListItem
import ru.nbsp.pushka.repository.subscription.ListItemRepository
import rx.Subscriber
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

/**
 * Created by Dimorinny on 12.04.16.
 */
class AutoCompleteControlPresenter @Inject constructor(
        @ApiRepository val apiListItemRepository: ListItemRepository) : BasePresenter {

    override var view: AutoCompleteControlView? = null
    val subscription: CompositeSubscription = CompositeSubscription()
    lateinit var attributes: ListAttributes

    override fun onCreate() {
        super.onCreate()
    }

    fun loadItems(query: String) {
        subscription.add(apiListItemRepository.getListItems(attributes.sourceId, attributes.listId, query)
                .subscribe(object : Subscriber<List<PresentationListItem>>() {
                    override fun onCompleted() {}

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }

                    override fun onNext(result: List<PresentationListItem>) {
                        view?.setItems(result)
                    }
                }))
    }

    override fun onDestroy() {
        subscription.unsubscribe()
        super.onDestroy()
    }
}