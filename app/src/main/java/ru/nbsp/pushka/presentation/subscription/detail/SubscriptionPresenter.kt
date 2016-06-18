package ru.nbsp.pushka.presentation.subscription.detail

import ru.nbsp.pushka.R
import ru.nbsp.pushka.bus.RxBus
import ru.nbsp.pushka.bus.event.subscription.LoadSourceAndSubscriptionEvent
import ru.nbsp.pushka.bus.event.subscription.UnsubscribeEvent
import ru.nbsp.pushka.di.annotation.StorageRepository
import ru.nbsp.pushka.presentation.core.base.BasePresenter
import ru.nbsp.pushka.presentation.core.model.source.PresentationSource
import ru.nbsp.pushka.presentation.core.model.subscription.PresentationSubscription
import ru.nbsp.pushka.presentation.core.state.State
import ru.nbsp.pushka.repository.source.SourcesRepository
import ru.nbsp.pushka.repository.subscription.SubscriptionRepository
import ru.nbsp.pushka.service.ServiceManager
import ru.nbsp.pushka.util.StringUtils
import rx.Observable
import rx.Subscriber
import rx.functions.Action1
import rx.subscriptions.CompositeSubscription
import java.util.*
import javax.inject.Inject

/**
 * Created by Dimorinny on 29.04.16.
 */
class SubscriptionPresenter
        @Inject constructor(
                @StorageRepository val sourcesRepository: SourcesRepository,
                @StorageRepository val storageSubscriptionRepository: SubscriptionRepository,
                val rxBus: RxBus,
                val serviceManager: ServiceManager,
                val stringUtils: StringUtils): BasePresenter {

    override var view: SubscriptionView? = null
    var source: PresentationSource? = null
    var subscription: PresentationSubscription? = null
    val compositeSubscription: CompositeSubscription = CompositeSubscription()

    override fun onCreate() {
        super.onCreate()

        observeLoadSourceAndSubscriptionEvent()
        observeUnsubscribeEvent()
    }

    private fun observeLoadSourceAndSubscriptionEvent() {
        compositeSubscription.add(rxBus.events(LoadSourceAndSubscriptionEvent::class.java)
                .flatMap {
                    when (it) {
                        is LoadSourceAndSubscriptionEvent.Success ->
                            loadSourceAndSubscriptionFromCacheObservable(it.sourceId, it.subscriptionId)

                        is LoadSourceAndSubscriptionEvent.Error ->
                            Observable.error(it.t)
                    }
                }
                .subscribe(LoadSubscriptionNetworkSubscriber()))
    }

    private fun observeUnsubscribeEvent() {
        compositeSubscription.add(rxBus.events(UnsubscribeEvent::class.java)
                .flatMap {
                    when (it) {
                        is UnsubscribeEvent.Success ->
                            Observable.just(it.id)

                        is UnsubscribeEvent.Error ->
                            Observable.error(it.t)
                    }
                }
                .subscribe(UnsubscribeSubscriber()))
    }

    fun loadSourceAndSubscriptionFromNetwork(sourceId: String, subscriptionId: String) {
        serviceManager.loadSourceAndSubscription(sourceId, subscriptionId)
    }

    fun loadSourceAndSubscriptionFromCache(sourceId: String, subscriptionId: String) {
        compositeSubscription.add(loadSourceAndSubscriptionFromCacheObservable(sourceId, subscriptionId)
                .subscribe(LoadSubscriptionCacheSubscriber()))
    }

    private fun loadSourceAndSubscriptionFromCacheObservable(sourceId: String,
            subscriptionId: String): Observable<PresentationSubscription> {

        return sourcesRepository.getSource(sourceId)
                .doOnNext(SourceLoadedAction())
                .flatMap { storageSubscriptionRepository.getSubscription(subscriptionId) }
    }

    fun changeSubscriptionButtonClicked(params: HashMap<String, String?>) {
//        if (view!!.validateFields()) {
//            view?.showUnsubscribeProgressDialog()
//            serviceManager.subscribe(source!!.id, params)
//        }
    }

    fun unsubscribeButtonClicked() {
        view?.showUnsubscribeProgressDialog()
        serviceManager.unsubscribe(subscription!!.id)
    }

    inner class SourceLoadedAction() : Action1<PresentationSource> {
        override fun call(result: PresentationSource) {
            if (result != source) {
                if (result.params.size != 0) {
                    view?.setParams(result.params)
                }
            }

            source = result
        }
    }

    inner class LoadSubscriptionCacheSubscriber : Subscriber<PresentationSubscription>() {
        override fun onCompleted() {}

        override fun onError(t: Throwable) {
            t.printStackTrace()
        }

        override fun onNext(result: PresentationSubscription) {
            view?.setState(State.STATE_NORMAL)

            if (result != subscription) {
                view?.setSubscriptionData(result)
                view?.setTitle(result.title)
            }

            subscription = result
        }
    }

    inner class LoadSubscriptionNetworkSubscriber : Subscriber<PresentationSubscription>() {
        override fun onCompleted() {}

        override fun onError(t: Throwable) {
            t.printStackTrace()

            if (subscription == null) {
                view?.setState(State.STATE_ERROR)
            }

            observeLoadSourceAndSubscriptionEvent()
        }

        override fun onNext(result: PresentationSubscription) {
            view?.setState(State.STATE_NORMAL)

            if (result != subscription) {
                view?.setSubscriptionData(result)
                view?.setTitle(result.title)
            }

            subscription = result
        }
    }

    inner class UnsubscribeSubscriber : Subscriber<String>() {
        override fun onCompleted() {}

        override fun onError(e: Throwable) {
            e.printStackTrace()
            view?.hideUnsubscribeProgressDialog()
            view?.showMessage(stringUtils.getString(R.string.subscription_unsubscribe_fail_message))
            observeUnsubscribeEvent()
        }

        override fun onNext(t: String) {
            view?.hideUnsubscribeProgressDialog()
            view?.showMessage(stringUtils.getString(R.string.subscription_unsubscribe_success_message))
            view?.closeScreen()
        }
    }

    override fun onDestroy() {
        compositeSubscription.unsubscribe()
        super.onDestroy()
    }
}