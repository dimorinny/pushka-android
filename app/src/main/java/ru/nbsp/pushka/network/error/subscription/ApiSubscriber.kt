package ru.nbsp.pushka.network.error.subscription

import ru.nbsp.pushka.network.error.ApiErrorException
import ru.nbsp.pushka.util.ErrorUtils
import rx.Subscriber
import java.net.UnknownHostException

/**
 * Created by Dimorinny on 24.05.16.
 */
class ApiSubscriber<T>(val delegate: ApiSubscriberDelegate<T>) : Subscriber<T>() {
    override fun onError(t: Throwable) {
        when (t) {
            is ApiErrorException -> delegate.onApiError(t, t.code)
            is UnknownHostException -> delegate.onApiError(t, ErrorUtils.CONNECTION_ERROR_CODE)
            else -> delegate.onError(t)
        }
    }

    override fun onCompleted() {
        delegate.onCompleted()
    }

    override fun onNext(data: T) {
        delegate.onNext(data)
    }
}