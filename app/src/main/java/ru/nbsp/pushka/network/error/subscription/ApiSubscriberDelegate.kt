package ru.nbsp.pushka.network.error.subscription

/**
 * Created by Dimorinny on 24.05.16.
 */

interface ApiSubscriberDelegate<T> {
    fun onApiError(t: Throwable, code: Int)
    fun onCompleted() {}
    fun onError(t: Throwable) {}
    fun baseErrorHandler(t: Throwable) {}
    fun onNext(data: T)
}