package ru.nbsp.pushka.iteractor

import rx.Observable
import rx.Scheduler
import rx.Subscriber
import rx.Subscription

/**
 * Created by Dimorinny on 17.02.16.
 */

abstract class BaseIteractor<ResultType, ParameterType>(val jobScheduler: Scheduler,
                                                        val resultScheduler: Scheduler) {

    abstract fun buildObservable(parameter: ParameterType?): Observable<ResultType>

    fun execute(parameter: ParameterType?, subscriber: Subscriber<ResultType>): Subscription {
        return buildObservable(parameter)
                .subscribeOn(jobScheduler)
                .observeOn(resultScheduler)
                .subscribe(subscriber)
    }

    fun execute(subscriber: Subscriber<ResultType>) {
        execute(null, subscriber)
    }
}