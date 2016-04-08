package ru.nbsp.pushka.presentation.subscription.subscribe

import ru.nbsp.pushka.interactor.subscription.ApiSubscriptionInteractor
import ru.nbsp.pushka.network.request.SubscribeRequest
import ru.nbsp.pushka.presentation.core.base.BasePresenter
import ru.nbsp.pushka.presentation.core.model.source.PresentationSource
import rx.Subscriber
import javax.inject.Inject

/**
 * Created by egor on 18.03.16.
 */
class SubscribePresenter
    @Inject constructor(val subscriptionInteractor: ApiSubscriptionInteractor): BasePresenter {

    override var view: SubscribeView? = null
    lateinit var source: PresentationSource

    override fun onCreate() {
        super.onCreate()
        view?.setParams(source.params)
    }

    fun subscribeButtonClicked() {
        if (view!!.validateFields()) {
            val params = view!!.getParamsMap()
            subscriptionInteractor.subscribe(SubscribeRequest(source.id, params))
                    .subscribe(SubscribeSourceSubscriber())
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