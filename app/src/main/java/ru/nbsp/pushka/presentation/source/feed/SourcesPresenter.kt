package ru.nbsp.pushka.presentation.source.feed

import ru.nbsp.pushka.interactor.source.SourceInteractor
import ru.nbsp.pushka.network.request.SubscribeRequest
import ru.nbsp.pushka.presentation.core.base.BasePresenter
import ru.nbsp.pushka.presentation.core.model.source.PresentationSource
import ru.nbsp.pushka.repository.source.SourcesRepository
import rx.Subscriber
import javax.inject.Inject

/**
 * Created by Dimorinny on 24.02.16.
 */
class SourcesPresenter
    @Inject constructor(val sourcesRepository: SourcesRepository,
                        val sourceInteractor: SourceInteractor) : BasePresenter {

    override var view: SourceView? = null

    fun loadSourcesFromCache() {
        sourcesRepository.getSources().subscribe(LoadSourcesSubscriber())
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
}