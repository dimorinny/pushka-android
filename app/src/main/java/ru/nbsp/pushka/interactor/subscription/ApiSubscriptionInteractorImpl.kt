package ru.nbsp.pushka.interactor.subscription

import ru.nbsp.pushka.network.request.SubscribeRequest
import ru.nbsp.pushka.network.service.PushkaSubscriptionService
import ru.nbsp.pushka.util.SchedulersUtils
import rx.Observable

/**
 * Created by Dimorinny on 27.02.16.
 */
class ApiSubscriptionInteractorImpl(
        val apiPushka: PushkaSubscriptionService,
        val schedulersUtils: SchedulersUtils) : ApiSubscriptionInteractor {

    override fun subscribe(subscribeRequest: SubscribeRequest): Observable<Any> {
        return apiPushka.subscribe(subscribeRequest)
                .compose(schedulersUtils.applySchedulers<Any>())
    }
}