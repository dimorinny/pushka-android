package ru.nbsp.pushka.util

import ru.nbsp.pushka.annotation.IOSched
import ru.nbsp.pushka.annotation.UISched
import rx.Observable
import rx.Scheduler
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Dimorinny on 21.02.16.
 */

@Singleton
class SchedulersManager
    @Inject constructor(
            @IOSched val ioScheduler: Scheduler,
            @UISched val uiScheduler: Scheduler) {

    fun <T>applySchedulers(): Observable.Transformer<T, T>  {
        return Observable.Transformer {
            it.subscribeOn(ioScheduler).observeOn(uiScheduler)
        }
    }
}