package ru.nbsp.pushka.bus

import rx.Observable
import rx.subjects.PublishSubject
import rx.subjects.SerializedSubject
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Dimorinny on 12.02.16.
 */

@Singleton
class RxBus @Inject constructor() {
    private val bus = SerializedSubject<Any, Any>(PublishSubject.create());

    fun post(event: Any) {
        bus.onNext(event)
    }

    fun <T> events(type: Class<T>): Observable<T> {
        return events().ofType(type)
    }

    fun events(): Observable<Any> {
        return bus.asObservable()
    }
}