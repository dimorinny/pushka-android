package ru.nbsp.pushka.presentation.device.feed

import ru.nbsp.pushka.annotation.StorageRepository
import ru.nbsp.pushka.bus.RxBus
import ru.nbsp.pushka.bus.event.device.LoadDevicesEvent
import ru.nbsp.pushka.presentation.core.base.BasePresenter
import ru.nbsp.pushka.presentation.core.model.device.PresentationDevice
import ru.nbsp.pushka.presentation.core.state.State
import ru.nbsp.pushka.repository.device.DeviceRepository
import ru.nbsp.pushka.service.ServiceManager
import rx.Observable
import rx.Subscriber
import rx.subscriptions.CompositeSubscription
import java.util.*
import javax.inject.Inject

/**
 * Created by Dimorinny on 31.03.16.
 */
class DevicesPresenter
    @Inject constructor(@StorageRepository val deviceRepository: DeviceRepository,
                        val rxBus: RxBus,
                        val serviceManager: ServiceManager): BasePresenter {

    override var view: DevicesView? = null

    val subscription: CompositeSubscription = CompositeSubscription()
    var devices: List<PresentationDevice> = ArrayList()

    override fun onCreate() {
        super.onCreate()

        observeLoadDevicesEvent()
    }

    private fun observeLoadDevicesEvent() {
        subscription.add(rxBus.events(LoadDevicesEvent::class.java)
                .flatMap {
                    when (it) {
                        is LoadDevicesEvent.Success -> deviceRepository.getDevices()
                        is LoadDevicesEvent.Error -> Observable.error(it.t)
                    }
                }
                .subscribe(LoadDevicesNetworkSubscriber()))
    }

    fun loadDevicesFromCache() {
        subscription.add(deviceRepository.getDevices().subscribe(LoadDevicesCacheSubscriber()))
    }

    fun loadDevicesFromServer() {
        serviceManager.loadDevices()
    }

    inner class LoadDevicesCacheSubscriber : Subscriber<List<PresentationDevice>>() {
        override fun onCompleted() {}

        override fun onError(t: Throwable) {
            t.printStackTrace()
        }

        override fun onNext(result: List<PresentationDevice>) {
            devices = result

            if (!result.isEmpty()) {
                view?.setState(State.STATE_NORMAL)
            }

            view?.setDevices(result)
        }
    }

    inner class LoadDevicesNetworkSubscriber : Subscriber<List<PresentationDevice>>() {
        override fun onCompleted() {}

        override fun onError(t: Throwable) {
            t.printStackTrace()

            view?.disableSwipeRefresh()
            if (devices.size == 0) {
                view?.setState(State.STATE_ERROR)
            }

            // Its workaround. I don't know, how to do it more elegant.
            this@DevicesPresenter.observeLoadDevicesEvent()
        }

        override fun onNext(result: List<PresentationDevice>) {
            devices = result
            view?.disableSwipeRefresh()
            view?.setState(if (result.isEmpty()) State.STATE_EMPTY else State.STATE_NORMAL)
            view?.setDevices(result)
        }
    }

    override fun onDestroy() {
        subscription.unsubscribe()
        super.onDestroy()
    }
}