package ru.nbsp.pushka.presentation.subscription.subscribe

import ru.nbsp.pushka.annotation.StorageRepository
import ru.nbsp.pushka.bus.RxBus
import ru.nbsp.pushka.bus.event.subscription.SubscribeEvent
import ru.nbsp.pushka.presentation.core.base.BasePresenter
import ru.nbsp.pushka.presentation.core.model.source.PresentationSource
import ru.nbsp.pushka.repository.source.SourcesRepository
import ru.nbsp.pushka.service.ServiceManager
import rx.Observable
import rx.Subscriber
import rx.subscriptions.CompositeSubscription
import java.util.*
import javax.inject.Inject

/**
 * Created by egor on 18.03.16.
 */
class SubscribePresenter
    @Inject constructor(@StorageRepository val sourcesRepository: SourcesRepository,
                        val rxBus: RxBus,
                        val serviceManager: ServiceManager): BasePresenter {

    override var view: SubscribeView? = null
    var source: PresentationSource? = null
    val subscription: CompositeSubscription = CompositeSubscription()

    override fun onCreate() {
        super.onCreate()

        observeSubscribe()
    }

    private fun observeSubscribe() {
        subscription.add(rxBus.events(SubscribeEvent::class.java)
                .flatMap {
                    when (it) {
                        is SubscribeEvent.Success -> Observable.just(Any())
                        is SubscribeEvent.Error -> Observable.error(it.t)
                    }
                }.subscribe(SubscribeSourceSubscriber()))
    }

    fun loadSourceFromCache(sourceId: String) {
        subscription.add(sourcesRepository.getSource(sourceId)
                .subscribe(LoadSourceSubscriber()))
    }

    fun subscribeButtonClicked(params: HashMap<String, String?>) {
        if (view!!.validateFields()) {
            view?.showSubscribeProgressDialog()
            serviceManager.subscribe(source!!.id, params)
        }
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

    inner class SubscribeSourceSubscriber : Subscriber<Any>() {
        override fun onCompleted() {}

        override fun onError(e: Throwable) {
            e.printStackTrace()
            view?.hideSubscribeProgressDialog()

            this@SubscribePresenter.observeSubscribe()
        }

        override fun onNext(result: Any) {
            view?.hideSubscribeProgressDialog()
        }
    }

    override fun onDestroy() {
        subscription.unsubscribe()
        super.onDestroy()
    }
}