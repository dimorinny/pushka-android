package ru.nbsp.pushka.presentation.subscription.subscribe

import ru.nbsp.pushka.annotation.StorageRepository
import ru.nbsp.pushka.interactor.subscription.ApiSubscriptionInteractor
import ru.nbsp.pushka.presentation.core.base.BasePresenter
import ru.nbsp.pushka.presentation.core.model.source.PresentationSource
import ru.nbsp.pushka.repository.source.SourcesRepository
import rx.Subscriber
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

/**
 * Created by egor on 18.03.16.
 */
class SubscribePresenter
    @Inject constructor(@StorageRepository val sourcesRepository: SourcesRepository,
                        val subscriptionInteractor: ApiSubscriptionInteractor): BasePresenter {

    override var view: SubscribeView? = null
    var source: PresentationSource? = null
    val subscription: CompositeSubscription = CompositeSubscription()

    fun loadSourceFromCache(sourceId: String) {
        subscription.add(sourcesRepository.getSource(sourceId)
                .subscribe(LoadSourceSubscriber()))
    }

    fun subscribeButtonClicked() {
//        if (view!!.validateFields()) {
//            val params = view!!.getParamsMap()
//            subscriptionInteractor.subscribe(SubscribeRequest(source.id, params))
//                    .subscribe(SubscribeSourceSubscriber())
//        }
    }

    inner class LoadSourceSubscriber : Subscriber<PresentationSource>() {
        override fun onCompleted() {}

        override fun onError(t: Throwable) {
            t.printStackTrace()
        }

        override fun onNext(result: PresentationSource) {
            if (result != source) {
                view?.setSourceData(result)
                view?.setTitle(result.name)
                if (result.params.size != 0) {
                    view?.setParams(result.params)
                }
            }

            source = result
        }
    }

    override fun onDestroy() {
        subscription.unsubscribe()
        super.onDestroy()
    }
}