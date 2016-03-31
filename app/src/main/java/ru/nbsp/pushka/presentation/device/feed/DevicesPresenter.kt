package ru.nbsp.pushka.presentation.device.feed

import ru.nbsp.pushka.annotation.ApiRepository
import ru.nbsp.pushka.presentation.core.base.BasePresenter
import ru.nbsp.pushka.presentation.core.model.device.PresentationDevice
import ru.nbsp.pushka.presentation.core.state.State
import ru.nbsp.pushka.repository.device.DevicesRepository
import rx.Subscriber
import rx.subscriptions.CompositeSubscription
import java.util.*
import javax.inject.Inject

/**
 * Created by Dimorinny on 31.03.16.
 */
class DevicesPresenter
    @Inject constructor(
            @ApiRepository val devicesRepository: DevicesRepository): BasePresenter {

    override var view: DevicesView? = null

    val subscription: CompositeSubscription = CompositeSubscription()
    var devices: List<PresentationDevice> = ArrayList()

    fun loadDevicesFromServer() {
        subscription.add(devicesRepository.getDevices()
                .subscribe(object : Subscriber<List<PresentationDevice>>() {
                    override fun onCompleted() {}

                    override fun onError(t: Throwable) {
                        t.printStackTrace()

                        if (devices.size == 0) {
                            view?.setState(State.STATE_ERROR)
                        }
                    }

                    override fun onNext(result: List<PresentationDevice>) {
                        devices = result
                        view?.setState(if (result.isEmpty()) State.STATE_EMPTY else State.STATE_NORMAL)
                        view?.setDevices(result)
                    }
                }))
    }

    override fun onDestroy() {
        subscription.unsubscribe()
        super.onDestroy()
    }
}