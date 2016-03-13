package ru.nbsp.pushka.presentation.source.feed

import ru.nbsp.pushka.annotation.StorageRepository
import ru.nbsp.pushka.bus.RxBus
import ru.nbsp.pushka.bus.event.LoadSourcesEvent
import ru.nbsp.pushka.interactor.source.ApiSourceInteractor
import ru.nbsp.pushka.network.request.SubscribeRequest
import ru.nbsp.pushka.presentation.core.base.BasePresenter
import ru.nbsp.pushka.presentation.core.model.source.PresentationCategory
import ru.nbsp.pushka.presentation.core.model.source.PresentationSource
import ru.nbsp.pushka.repository.source.SourcesRepository
import ru.nbsp.pushka.service.ServiceManager
import rx.Observable
import rx.Subscriber
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

/**
 * Created by Dimorinny on 24.02.16.
 */
class SourcesPresenter
    @Inject constructor(@StorageRepository val storageSourcesRepository: SourcesRepository,
                        val rxBus: RxBus,
                        val sourceInteractor: ApiSourceInteractor,
                        val serviceManager: ServiceManager) : BasePresenter {

    override var view: SourceView? = null
    val subscription: CompositeSubscription = CompositeSubscription()
    lateinit var category: PresentationCategory

    override fun onCreate() {
        super.onCreate()

        subscription.add(rxBus.events(LoadSourcesEvent::class.java)
                .flatMap {
                    when (it) {
                        is LoadSourcesEvent.Success -> storageSourcesRepository.getSources(category.id)
                        is LoadSourcesEvent.Error -> Observable.error(it.t)
                    }
                }.subscribe(LoadSourcesSubscriber()))
    }

    fun loadSourcesFromServer() {
        serviceManager.loadSources(category.id)
    }

    fun loadSourcesFromCache() {
        subscription.add(storageSourcesRepository.getSources(category.id).subscribe(LoadSourcesSubscriber()))
    }

    fun onSourceClicked() {
        sourceInteractor.subscribe(SubscribeRequest("news_gazetaru", mapOf(
                Pair("category", "main"))))
                .subscribe(SubscribeSourceSubscriber())
    }

    inner class LoadSourcesSubscriber : Subscriber<List<PresentationSource>>() {
        override fun onCompleted() {}

        override fun onError(t: Throwable) {
            t.printStackTrace()
        }

        override fun onNext(alerts: List<PresentationSource>) {
            view?.setSources(alerts)
        }
    }

    inner class SubscribeSourceSubscriber : Subscriber<Any>() {
        override fun onCompleted() {}

        override fun onError(t: Throwable) {
            t.printStackTrace()
        }

        override fun onNext(any: Any) {
        }
    }

    override fun onDestroy() {
        subscription.unsubscribe()
        super.onDestroy()
    }
}