package ru.nbsp.pushka.iteractor

import rx.Observable
import rx.Scheduler

/**
 * Created by Dimorinny on 17.02.16.
 */

abstract class BaseIteractor<ResultType, ParameterType>(val jobScheduler: Scheduler,
                                                        val resultScheduler: Scheduler) {

    abstract fun buildObservable(parameter: ParameterType?): Observable<ResultType>

    fun execute(parameter: ParameterType?): Observable<ResultType> {
        return buildObservable(parameter)
                .subscribeOn(jobScheduler)
                .observeOn(resultScheduler)
    }
}