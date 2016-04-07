package ru.nbsp.pushka.presentation.subscription.feed

import ru.nbsp.pushka.annotation.StorageRepository
import ru.nbsp.pushka.bus.RxBus
import ru.nbsp.pushka.bus.event.subscription.LoadSubscriptionsEvent
import ru.nbsp.pushka.presentation.core.base.BasePresenter
import ru.nbsp.pushka.presentation.core.model.subscription.PresentationSubscription
import ru.nbsp.pushka.presentation.core.state.State
import ru.nbsp.pushka.repository.subscription.SubscriptionRepository
import ru.nbsp.pushka.service.ServiceManager
import rx.Observable
import rx.Subscriber
import rx.subscriptions.CompositeSubscription
import java.util.*
import javax.inject.Inject

/**
 * Created by Dimorinny on 26.02.16.
 */
class SubscriptionsPresenter
    @Inject constructor(@StorageRepository val storageSubscriptionsRepository: SubscriptionRepository,
                        val rxBus: RxBus,
                        val serviceManager: ServiceManager) : BasePresenter {

    override var view: SubscriptionsView? = null

    val subscription: CompositeSubscription = CompositeSubscription()
    var subscriptions: List<PresentationSubscription> = ArrayList()

    override fun onCreate() {
        super.onCreate()

        subscription.add(rxBus.events(LoadSubscriptionsEvent::class.java)
                .flatMap {
                    when (it) {
                        is LoadSubscriptionsEvent.Success -> storageSubscriptionsRepository.getSubscriptions()
                        is LoadSubscriptionsEvent.Error -> Observable.error(it.t)
                    }
                }.subscribe(LoadSubscriptionsNetworkSubscriber()))
    }

    fun loadSubscriptionsFromCache() {
        subscription.add(storageSubscriptionsRepository.getSubscriptions().subscribe(LoadSubscriptionsCacheSubscriber()))
    }

    fun loadSubscriptionsFromServer() {
        serviceManager.loadSubscriptions()
    }

    inner class LoadSubscriptionsCacheSubscriber : Subscriber<List<PresentationSubscription>>() {
        override fun onCompleted() {}

        override fun onError(t: Throwable) {
            t.printStackTrace()
        }

        override fun onNext(result: List<PresentationSubscription>) {
            subscriptions = result

            if (!result.isEmpty()) {
                view?.setState(State.STATE_NORMAL)
            }

            view?.setSubscriptions(result)
        }
    }

    inner class LoadSubscriptionsNetworkSubscriber : Subscriber<List<PresentationSubscription>>() {
        override fun onCompleted() {}

        override fun onError(t: Throwable) {
            t.printStackTrace()

//            view?.disableSwipeRefresh()
            if (subscriptions.size == 0) {
                view?.setState(State.STATE_ERROR)
            }
        }

        override fun onNext(result: List<PresentationSubscription>) {
            subscriptions = result
//            view?.disableSwipeRefresh()
            view?.setState(if (result.isEmpty()) State.STATE_EMPTY else State.STATE_NORMAL)
            view?.setSubscriptions(result)
        }
    }

    override fun onDestroy() {
        subscription.unsubscribe()
        super.onDestroy()
    }
}