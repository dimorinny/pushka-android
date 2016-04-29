package ru.nbsp.pushka.interactor.subscription

import ru.nbsp.pushka.mapper.presentation.subscription.PresentationSubscriptionMapper
import ru.nbsp.pushka.network.request.SubscribeRequest
import ru.nbsp.pushka.network.service.PushkaSubscriptionService
import ru.nbsp.pushka.presentation.core.model.subscription.PresentationSubscription
import ru.nbsp.pushka.util.SchedulersUtils
import rx.Observable

/**
 * Created by Dimorinny on 27.02.16.
 */
class ApiSubscriptionInteractorImpl(
        val apiPushka: PushkaSubscriptionService,
        val presentationSubscriptionMapper: PresentationSubscriptionMapper,
        val schedulersUtils: SchedulersUtils) : ApiSubscriptionInteractor {

    override fun subscribe(subscribeRequest: SubscribeRequest): Observable<PresentationSubscription> {
        return apiPushka.subscribe(subscribeRequest)
                .map { presentationSubscriptionMapper.fromNetworkSubscription(it.subscription) }
                .compose(schedulersUtils.applySchedulers<PresentationSubscription>())
    }
}