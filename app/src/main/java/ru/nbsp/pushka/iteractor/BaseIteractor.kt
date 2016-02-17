package ru.nbsp.pushka.iteractor

import rx.Observable
import rx.Scheduler
import rx.Subscriber
import rx.subscriptions.CompositeSubscription

/**
 * Created by Dimorinny on 17.02.16.
 */

abstract class BaseIteractor<ResultType, ParameterType>(val jobScheduler: Scheduler,
                                                        val resultScheduler: Scheduler) {

    private val subscription = CompositeSubscription()

    abstract fun buildObservable(parameter: ParameterType?): Observable<ResultType>

    fun execute(parameter: ParameterType?, subscriber: Subscriber<ResultType>) {
        subscription.add(buildObservable(parameter)
                .subscribeOn(jobScheduler)
                .observeOn(resultScheduler)
                .subscribe(subscriber))
    }

    fun execute(subscriber: Subscriber<ResultType>) {
        execute(null, subscriber)
    }

    fun unsubscribe() {
        subscription.clear()
    }
}