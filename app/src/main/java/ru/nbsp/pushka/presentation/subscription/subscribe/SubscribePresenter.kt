package ru.nbsp.pushka.presentation.subscription.subscribe

import android.util.Log
import ru.nbsp.pushka.interactor.subscription.ApiSubscriptionInteractor
import ru.nbsp.pushka.network.request.SubscribeRequest
import ru.nbsp.pushka.presentation.core.base.BasePresenter
import ru.nbsp.pushka.presentation.core.model.source.PresentationParam
import ru.nbsp.pushka.presentation.core.model.source.PresentationSource
import ru.nbsp.pushka.presentation.source.feed.SourcesPresenter
import rx.Subscriber
import java.util.*
import javax.inject.Inject

/**
 * Created by egor on 18.03.16.
 */
class SubscribePresenter
    @Inject constructor(val subscriptionInteractor: ApiSubscriptionInteractor): BasePresenter {

    override var view: SubscribeView? = null

    lateinit var srcId: String

    fun setSource(source: PresentationSource) {
        srcId = source.id
        view?.setParams(source.params)
    }

    fun onButtonClick() {
        if (view!!.validateFields()) {
            val map = view!!.getParamsMap()
            subscriptionInteractor.subscribe(SubscribeRequest(srcId, map))
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