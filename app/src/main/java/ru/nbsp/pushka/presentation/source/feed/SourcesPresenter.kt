package ru.nbsp.pushka.presentation.source.feed

import ru.nbsp.pushka.annotation.StorageRepository
import ru.nbsp.pushka.bus.RxBus
import ru.nbsp.pushka.bus.event.source.LoadSourcesEvent
import ru.nbsp.pushka.presentation.core.base.BasePresenter
import ru.nbsp.pushka.presentation.core.model.source.PresentationCategory
import ru.nbsp.pushka.presentation.core.model.source.PresentationSource
import ru.nbsp.pushka.presentation.core.state.State
import ru.nbsp.pushka.repository.source.SourcesRepository
import ru.nbsp.pushka.service.ServiceManager
import rx.Observable
import rx.Subscriber
import rx.subscriptions.CompositeSubscription
import java.util.*
import javax.inject.Inject

/**
 * Created by Dimorinny on 24.02.16.
 */
class SourcesPresenter
    @Inject constructor(@StorageRepository val storageSourcesRepository: SourcesRepository,
                        val rxBus: RxBus,
                        val serviceManager: ServiceManager) : BasePresenter {

    override var view: SourceView? = null
    lateinit var category: PresentationCategory
    val subscription: CompositeSubscription = CompositeSubscription()
    var sources: List<PresentationSource> = ArrayList()

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

    fun onSourceClicked(index: Int) {
        view?.openSubscribeScreen(sources[index])
    }

    inner class LoadSourcesSubscriber : Subscriber<List<PresentationSource>>() {
        override fun onCompleted() {}

        override fun onError(t: Throwable) {
            t.printStackTrace()

            if (sources.size == 0) {
                view?.setState(State.STATE_ERROR)
            }
        }

        override fun onNext(result: List<PresentationSource>) {
            sources = result
            
            view?.setState(if (result.isEmpty()) State.STATE_EMPTY else State.STATE_NORMAL)
            view?.setSources(result)
        }
    }

    override fun onDestroy() {
        subscription.unsubscribe()
        super.onDestroy()
    }
}