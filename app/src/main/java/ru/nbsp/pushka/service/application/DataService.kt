package ru.nbsp.pushka.service.application

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import ru.nbsp.pushka.BaseApplication
import ru.nbsp.pushka.interactor.data.DataInteractor
import rx.Subscriber
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

/**
 * Created by Dimorinny on 26.04.16.
 */
class DataService : Service() {

    @Inject
    lateinit var dataInteractor: DataInteractor

    private val subscription = CompositeSubscription()

    companion object {
        const val ARG_SERVICE_COMMAND = "arg_service_command"
        const val COMMAND_CLEAR_ALL = "command_load_alerts"
    }

    override fun onBind(intent: Intent?): IBinder? { return null }

    override fun onCreate() {
        super.onCreate()
        BaseApplication.graph.inject(this)
    }

    override fun onDestroy() {
        subscription.unsubscribe()
        super.onDestroy()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent == null) {
            return START_NOT_STICKY
        }

        val command = intent.getStringExtra(ARG_SERVICE_COMMAND)

        when (command) {
            COMMAND_CLEAR_ALL -> {
                handleClearAllCommand(startId)
            }
        }

        return START_NOT_STICKY
    }

    private fun handleClearAllCommand(startId: Int) {
        dataInteractor.clearAll()
                .subscribe(ClearAllSubscriber(startId))
    }

    inner class ClearAllSubscriber(val startId: Int) : Subscriber<Any>() {
        override fun onCompleted() {}

        override fun onError(t: Throwable) {
            t.printStackTrace()
            Log.d("Clearing", "Error on clearing data")
            stopSelf(startId)
        }

        override fun onNext(result: Any) {
            Log.d("Clearing", "Clearing success")
            stopSelf(startId)
        }
    }
}