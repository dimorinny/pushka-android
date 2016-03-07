package ru.nbsp.pushka.interactor.source

import ru.nbsp.pushka.network.request.SubscribeRequest
import rx.Observable

/**
 * Created by Dimorinny on 27.02.16.
 */
interface ApiSourceInteractor {
    fun subscribe(subscribeRequest: SubscribeRequest): Observable<Any>
}