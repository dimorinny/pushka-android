package ru.nbsp.pushka.presentation.subscription.detail

import ru.nbsp.pushka.annotation.StorageRepository
import ru.nbsp.pushka.bus.RxBus
import ru.nbsp.pushka.bus.event.subscription.LoadSourceAndSubscriptionEvent
import ru.nbsp.pushka.presentation.core.base.BasePresenter
import ru.nbsp.pushka.presentation.core.model.source.PresentationSource
import ru.nbsp.pushka.presentation.core.model.subscription.PresentationSubscription
import ru.nbsp.pushka.repository.source.SourcesRepository
import ru.nbsp.pushka.repository.subscription.SubscriptionRepository
import ru.nbsp.pushka.service.ServiceManager
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
                val serviceManager: ServiceManager): BasePresenter {

    override var view: SubscriptionView? = null
    var source: PresentationSource? = null
    var subscription: PresentationSubscription? = null
    val compositeSubscription: CompositeSubscription = CompositeSubscription()

    override fun onCreate() {
        super.onCreate()

        observeLoadSourceAndSubscriptionEvent()
    }

    private fun observeLoadSourceAndSubscriptionEvent() {
        compositeSubscription.add(rxBus.events(LoadSourceAndSubscriptionEvent::class.java)
                .flatMap {
                    when (it) {
                        is LoadSourceAndSubscriptionEvent.Success -> loadSourceAndSubscriptionFromCacheObservable(it.sourceId, it.subscriptionId)
                        is LoadSourceAndSubscriptionEvent.Error -> Observable.error(it.t)
                    }
                }
                .subscribe(LoadSubscriptionSubscriber()))
    }

    fun loadSourceAndSubscriptionFromNetwork(sourceId: String, subscriptionId: String) {
        serviceManager.loadSourceAndSubscription(sourceId, subscriptionId)
    }

    fun loadSourceAndSubscriptionFromCache(sourceId: String, subscriptionId: String) {
        compositeSubscription.add(loadSourceAndSubscriptionFromCacheObservable(sourceId, subscriptionId)
                .subscribe(LoadSubscriptionSubscriber()))
    }

    private fun loadSourceAndSubscriptionFromCacheObservable(sourceId: String,
            subscriptionId: String): Observable<PresentationSubscription> {

        return sourcesRepository.getSource(sourceId)
                .doOnNext(SourceLoadedAction())
                .flatMap { storageSubscriptionRepository.getSubscription(subscriptionId) }
    }

    fun subscribeButtonClicked(params: HashMap<String, String?>) {
//        if (view!!.validateFields()) {
//            view?.showUnsubscribeProgressDialog()
//            serviceManager.subscribe(source!!.id, params)
//        }
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

    inner class LoadSubscriptionSubscriber : Subscriber<PresentationSubscription>() {
        override fun onCompleted() {}

        override fun onError(t: Throwable) {
            t.printStackTrace()
        }

        override fun onNext(result: PresentationSubscription) {
            if (result != subscription) {
                view?.setSubscriptionData(result)
                view?.setTitle(result.title)
            }

            subscription = result
        }
    }

    override fun onDestroy() {
        compositeSubscription.unsubscribe()
        super.onDestroy()
    }
}