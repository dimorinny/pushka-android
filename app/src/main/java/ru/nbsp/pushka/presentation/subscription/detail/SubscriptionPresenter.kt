package ru.nbsp.pushka.presentation.subscription.detail

import ru.nbsp.pushka.annotation.StorageRepository
import ru.nbsp.pushka.bus.RxBus
import ru.nbsp.pushka.presentation.core.base.BasePresenter
import ru.nbsp.pushka.presentation.core.model.source.PresentationSource
import ru.nbsp.pushka.presentation.core.model.subscription.PresentationSubscription
import ru.nbsp.pushka.repository.source.SourcesRepository
import ru.nbsp.pushka.repository.subscription.SubscriptionRepository
import ru.nbsp.pushka.service.ServiceManager
import rx.Subscriber
import rx.subscriptions.CompositeSubscription
import java.util.*
import javax.inject.Inject

/**
 * Created by Dimorinny on 29.04.16.
 */
class SubscriptionPresenter
        @Inject constructor(
                @StorageRepository val sourcesRepository: SourcesRepository,
                @StorageRepository val subscriptionRepository: SubscriptionRepository,
                val rxBus: RxBus,
                val serviceManager: ServiceManager): BasePresenter {

    override var view: SubscriptionView? = null
    var source: PresentationSource? = null
    var subscription: PresentationSubscription? = null
    val compositeSubscription: CompositeSubscription = CompositeSubscription()

    fun loadSourceAndSubscriptionFromCache(sourceId: String, subscriptionId: String) {
        compositeSubscription.add(sourcesRepository.getSource(sourceId)
                .doOnNext {
                    if (it != source) {
                        if (it.params.size != 0) {
                            view?.setParams(it.params)
                        }
                    }

                    source = it
                }
                .flatMap { subscriptionRepository.getSubscription(subscriptionId) }
                .subscribe(LoadSubscriptionSubscriber()))
    }

    fun subscribeButtonClicked(params: HashMap<String, String?>) {
        if (view!!.validateFields()) {
            view?.showUnsubscribeProgressDialog()
            serviceManager.subscribe(source!!.id, params)
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