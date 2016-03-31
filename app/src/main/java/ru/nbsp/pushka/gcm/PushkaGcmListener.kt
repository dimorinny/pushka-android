package ru.nbsp.pushka.gcm

import android.os.Bundle
import android.util.Log
import eu.inloop.easygcm.GcmListener
import ru.nbsp.pushka.BaseApplication
import ru.nbsp.pushka.interactor.device.ApiDeviceInteractor
import rx.Subscriber
import javax.inject.Inject

/**
 * Created by Dimorinny on 31.03.16.
 */
class PushkaGcmListener : GcmListener {

    @Inject
    lateinit var apiDeviceInteractor: ApiDeviceInteractor

    constructor() {
        BaseApplication.graph.inject(this)
    }

    override fun sendRegistrationIdToBackend(registrationId: String) {
        Log.v("PushkaGcmListener", "Send registration id called refId: $registrationId")
        apiDeviceInteractor.addGcmDevice(registrationId)
                .subscribe(object: Subscriber<Any>() {
                    override fun onCompleted() {}

                    override fun onError(t: Throwable) {
                        t.printStackTrace()
                    }

                    override fun onNext(result: Any) {
                        Log.v("PushkaGcmListener", "On server result: ${result.toString()}")
                    }
                })
    }

    override fun onMessage(from: String, data: Bundle) {
        Log.v("PushkaGcmListener", "On message called from: $from + ${data.toString()}")
    }
}