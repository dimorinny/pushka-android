package ru.nbsp.pushka.presentation.source.feed

import ru.nbsp.pushka.api.model.source.Source
import ru.nbsp.pushka.presentation.core.base.BasePresenter
import ru.nbsp.pushka.repository.source.SourcesRepository
import rx.Subscriber
import javax.inject.Inject

/**
 * Created by Dimorinny on 24.02.16.
 */
class SourcesPresenter
    @Inject constructor(val sourcesRepository: SourcesRepository) : BasePresenter {

    override var view: SourceView? = null

    fun loadSourcesFromCache() {
        sourcesRepository.getSources().subscribe(LoadSourcesSubscriber())
    }

    inner class LoadSourcesSubscriber : Subscriber<List<Source>>() {
        override fun onCompleted() {}

        override fun onError(t: Throwable) {
            t.printStackTrace()
        }

        override fun onNext(alerts: List<Source>) {
            view?.setSources(alerts)
        }
    }
}