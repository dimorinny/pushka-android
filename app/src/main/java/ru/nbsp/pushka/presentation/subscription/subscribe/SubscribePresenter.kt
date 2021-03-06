package ru.nbsp.pushka.presentation.subscription.subscribe

import ru.nbsp.pushka.R
import ru.nbsp.pushka.bus.RxBus
import ru.nbsp.pushka.bus.event.source.LoadSourceEvent
import ru.nbsp.pushka.bus.event.subscription.SubscribeEvent
import ru.nbsp.pushka.di.annotation.StorageRepository
import ru.nbsp.pushka.network.error.subscription.ApiSubscriber
import ru.nbsp.pushka.network.error.subscription.ApiSubscriberDelegate
import ru.nbsp.pushka.network.error.subscription.annotation.ErrorHandler
import ru.nbsp.pushka.presentation.core.base.BasePresenter
import ru.nbsp.pushka.presentation.core.model.source.PresentationSource
import ru.nbsp.pushka.presentation.core.state.State
import ru.nbsp.pushka.repository.source.SourcesRepository
import ru.nbsp.pushka.service.ServiceManager
import ru.nbsp.pushka.util.ErrorUtils
import ru.nbsp.pushka.util.StringUtils
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
                        val errorUtils: ErrorUtils,
                        val stringUtils: StringUtils): BasePresenter {

    companion object {
        const val SOURCE_URL = "https://dev.pushka.xyz/source/"
    }

    override var view: SubscribeView? = null
    var source: PresentationSource? = null
    val subscription: CompositeSubscription = CompositeSubscription()

    override fun onCreate() {
        super.onCreate()

        observeSubscribe()
        observeLoadSource()
    }

    private fun observeLoadSource() {
        subscription.add(rxBus.events(LoadSourceEvent::class.java)
                .flatMap {
                    when (it) {
                        is LoadSourceEvent.Success -> sourcesRepository.getSource(it.sourceId)
                        is LoadSourceEvent.Error -> Observable.error(it.t)
                    }
                }.subscribe(ApiSubscriber(LoadSourceFromServerSubscription())))
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
                .subscribe(LoadSourceFromCacheSubscriber()))
    }

    fun loadSourceFromServer(sourceId: String) {
        serviceManager.loadSource(sourceId)
    }

    fun sharedButtonClicked() {
        if (source != null) {
            view?.showShareMenu(source!!.name, SOURCE_URL + source!!.id)
        }
    }

    fun subscribeButtonClicked(sound: Boolean, notification: Boolean, params: HashMap<String, String?>) {
        if (view!!.validateFields()) {
            view?.showSubscribeProgressDialog()
            serviceManager.subscribe(source!!.id, sound, notification, params)
        }
    }

    inner class LoadSourceFromCacheSubscriber : Subscriber<PresentationSource>() {
        override fun onCompleted() {}

        override fun onError(t: Throwable) {
            t.printStackTrace()
        }

        override fun onNext(result: PresentationSource) {
            view?.setState(State.STATE_NORMAL)

            if (result != source) {
                view?.setSourceData(result)
                if (result.params.size != 0) {
                    view?.setParams(result.params)
                }
            }

            source = result
        }
    }

    inner class LoadSourceFromServerSubscription : ApiSubscriberDelegate<PresentationSource> {

        override fun baseErrorHandler(t: Throwable) {
            t.printStackTrace()

            if (source == null) {
                view?.setState(State.STATE_ERROR)
            }

            observeLoadSource()
        }

        override fun onApiError(t: Throwable, code: Int) {}

        override fun onNext(data: PresentationSource) {
            view?.setState(State.STATE_NORMAL)

            if (data != source) {
                view?.setSourceData(data)
                if (data.params.size != 0) {
                    view?.setParams(data.params)
                }
            }

            source = data
        }
    }

    inner class SubscribeSourceSubscriber : ApiSubscriberDelegate<Any> {

        @ErrorHandler(code=ErrorUtils.CONNECTION_ERROR_CODE)
        fun handleConnectionError(t: Throwable, code: Int) {
            view?.showSubscribeConnectionError(errorUtils.errorMessage(code))
        }

        override fun onApiError(t: Throwable, code: Int) {
            view?.showError(errorUtils.errorMessage(code))
        }

        override fun baseErrorHandler(t: Throwable) {
            t.printStackTrace()
            view?.hideSubscribeProgressDialog()
            observeSubscribe()
        }

        override fun onNext(data: Any) {
            view?.hideSubscribeProgressDialog()
            view?.showMessage(stringUtils.getString(R.string.subscription_subscribe_success_message))
            view?.closeScreen()
        }
    }

    override fun onDestroy() {
        subscription.unsubscribe()
        super.onDestroy()
    }
}