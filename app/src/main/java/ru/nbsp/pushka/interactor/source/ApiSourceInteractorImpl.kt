package ru.nbsp.pushka.interactor.source

import ru.nbsp.pushka.network.request.SubscribeRequest
import ru.nbsp.pushka.network.service.PushkaSourceService
import ru.nbsp.pushka.util.SchedulersUtils
import rx.Observable

/**
 * Created by Dimorinny on 27.02.16.
 */
class ApiSourceInteractorImpl(
        val apiPushka: PushkaSourceService,
        val schedulersUtils: SchedulersUtils) : ApiSourceInteractor {

    override fun subscribe(subscribeRequest: SubscribeRequest): Observable<Any> {
        return apiPushka.subscribe(subscribeRequest).compose(schedulersUtils.applySchedulers<Any>())
    }
}