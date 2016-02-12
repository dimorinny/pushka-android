package ru.nbsp.pushka.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import ru.nbsp.pushka.BaseApplication
import ru.nbsp.pushka.bus.RxBus
import javax.inject.Inject

/**
 * Created by Dimorinny on 12.02.16.
 */
class ApiPushkaService : Service() {

    // Use rxBus for communicate with presenters
    @Inject
    lateinit var bus: RxBus

    companion object {
        // Args
        const val ARG_SERVICE_COMMAND = "arg_service_command"

        // Commands
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        BaseApplication.graph.inject(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent == null) {
            return START_NOT_STICKY
        }

        val command = intent.getStringExtra(ARG_SERVICE_COMMAND)

        when (command) {
        // TODO: execute command
        }

        // TODO: think about service flag
        return START_NOT_STICKY
    }
}