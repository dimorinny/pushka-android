package ru.nbsp.pushka.service

import android.app.Service
import ru.nbsp.pushka.bus.RxBus
import ru.nbsp.pushka.bus.event.BaseEvent
import rx.Subscriber

/**
 * Created by Dimorinny on 28.04.16.
 */
abstract class BaseEventSubscriber(
        val service: Service,
        val startId: Int,
        val bus: RxBus) : Subscriber<Any>() {

    abstract fun error(t: Throwable): BaseEvent
    abstract fun success(): BaseEvent

    override fun onCompleted() {}

    override fun onError(t: Throwable) {
        bus.post(error(t))
        service.stopSelf(startId)
    }

    override fun onNext(result: Any) {
        bus.post(success())
        service.stopSelf(startId)
    }
}