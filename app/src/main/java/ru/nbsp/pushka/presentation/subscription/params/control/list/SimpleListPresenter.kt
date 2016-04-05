package ru.nbsp.pushka.presentation.subscription.params.control

import ru.nbsp.pushka.annotation.StorageRepository
import ru.nbsp.pushka.bus.RxBus
import ru.nbsp.pushka.bus.event.alert.LoadAlertsEvent
import ru.nbsp.pushka.bus.event.LoadSimpleListEvent
import ru.nbsp.pushka.presentation.alert.feed.AlertsView
import ru.nbsp.pushka.presentation.core.base.BasePresenter
import ru.nbsp.pushka.presentation.core.model.source.control.SimpleListControlItem
import ru.nbsp.pushka.presentation.core.state.State
import ru.nbsp.pushka.repository.alert.AlertsRepository
import ru.nbsp.pushka.repository.list.SimpleListRepository
import rx.Observable
import rx.Subscriber
import rx.subscriptions.CompositeSubscription
import java.util.*
import javax.inject.Inject

/**
 * Created by egor on 20.03.16.
 */
class SimpleListPresenter
@Inject constructor(
        val rxBus: RxBus): BasePresenter {
    override var view: SimpleListView? = null



    var items: List<SimpleListControlItem> = ArrayList()

    override fun onCreate() {
        super.onCreate()
        val subscription = CompositeSubscription()

        throw UnsupportedOperationException("get list from api here")
    }

    inner class LoadItemsNetworkSubscriber : Subscriber<List<SimpleListControlItem>>() {
        override fun onCompleted() {}

        override fun onError(t: Throwable) {
            t.printStackTrace()

            if (items.size == 0) {
                view?.setState(State.STATE_ERROR)
//                view?.setToolbarState(State.STATE_NORMAL)
            } else {
//                view?.setToolbarState(State.STATE_ERROR)
            }
        }

        override fun onNext(result: List<SimpleListControlItem>) {
            items = result
//            view?.setToolbarState(State.STATE_NORMAL)
            view?.setState(if (result.isEmpty()) State.STATE_EMPTY else State.STATE_NORMAL)
            view?.setItems(result)
        }
    }

    fun onAlertClicked(index: Int) {
        view!!.returnResult(items[index].value)
    }
}