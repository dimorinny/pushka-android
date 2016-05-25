package ru.nbsp.pushka.presentation.subscription.subscribe

import ru.nbsp.pushka.bus.RxBus
import ru.nbsp.pushka.bus.event.subscription.SubscribeEvent
import ru.nbsp.pushka.di.annotation.StorageRepository
import ru.nbsp.pushka.network.error.subscription.ApiSubscriber
import ru.nbsp.pushka.network.error.subscription.ApiSubscriberDelegate
import ru.nbsp.pushka.network.error.subscription.annotation.ErrorHandler
import ru.nbsp.pushka.presentation.core.base.BasePresenter
import ru.nbsp.pushka.presentation.core.model.source.PresentationSource
import ru.nbsp.pushka.repository.source.SourcesRepository
import ru.nbsp.pushka.service.ServiceManager
import ru.nbsp.pushka.util.ErrorUtils
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
                        val serviceManager: ServiceManager,
                        val errorUtils: ErrorUtils): BasePresenter {

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
                }.subscribe(ApiSubscriber(SubscribeSourceSubscriber())))
    }

    fun loadSourceFromCache(sourceId: String) {
        subscription.add(sourcesRepository.getSource(sourceId)
                .subscribe(LoadSourceSubscriber()))
    }

    fun subscribeButtonClicked(sound: Boolean, notification: Boolean, params: HashMap<String, String?>) {
        if (view!!.validateFields()) {
            view?.showSubscribeProgressDialog()
            serviceManager.subscribe(source!!.id, sound, notification, params)
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

    inner class SubscribeSourceSubscriber : ApiSubscriberDelegate<Any> {

        @ErrorHandler(code=ErrorUtils.CONNECTION_ERROR_CODE)
        fun handleConnectionError(t: Throwable, code: Int) {
            t.printStackTrace()

            view?.hideSubscribeProgressDialog()
            view?.showSubscribeConnectionError(errorUtils.errorMessage(code))

            observeSubscribe()
        }

        override fun onApiError(t: Throwable, code: Int) {
            t.printStackTrace()

            view?.hideSubscribeProgressDialog()
            view?.showError(errorUtils.errorMessage(code))

            observeSubscribe()
        }

        override fun onError(t: Throwable) {
            t.printStackTrace()
            view?.hideSubscribeProgressDialog()

            observeSubscribe()
        }

        override fun onNext(data: Any) {
            view?.hideSubscribeProgressDialog()
        }
    }

    override fun onDestroy() {
        subscription.unsubscribe()
        super.onDestroy()
    }
}