package ru.nbsp.pushka.gcm

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.NotificationCompat
import android.util.Log
import com.github.salomonbrys.kotson.fromJson
import com.google.gson.Gson
import eu.inloop.easygcm.GcmListener
import ru.nbsp.pushka.BaseApplication
import ru.nbsp.pushka.interactor.device.ApiDeviceInteractor
import ru.nbsp.pushka.network.model.alert.NetworkAlert
import ru.nbsp.pushka.presentation.alert.detail.AlertActivity
import ru.nbsp.pushka.util.NotificationIdUtils
import ru.nbsp.pushka.util.SourceIconUtils
import rx.Subscriber
import javax.inject.Inject

/**
 * Created by Dimorinny on 31.03.16.
 */
class PushkaGcmListener : GcmListener {

    companion object {
        const val KEY_ALERT = "alert"
    }

    @Inject
    lateinit var apiDeviceInteractor: ApiDeviceInteractor

    @Inject
    lateinit var context: Context

    @Inject
    lateinit var notificationManager: NotificationManager

    @Inject
    lateinit var gson: Gson

    @Inject
    lateinit var notificationIdUtils: NotificationIdUtils

    @Inject
    lateinit var iconUtils: SourceIconUtils

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

        val alert = gson.fromJson<NetworkAlert>(data.getString(KEY_ALERT))

        val mBuilder = NotificationCompat
                .Builder(context)
                .setPriority(1)
                .setColor(Color.parseColor(alert.notification.color))
                .setSmallIcon(iconUtils.getIcon(alert.notification.icon))
                .setContentTitle(alert.notification.title)
                .setContentText(alert.notification.description)

        val actionIntent = Intent(context, AlertActivity::class.java)
        actionIntent.putExtra(AlertActivity.ARG_ALERT_ID, alert.id)
        actionIntent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP

        mBuilder.setContentIntent(PendingIntent.getActivity(context, 0, actionIntent, PendingIntent.FLAG_UPDATE_CURRENT))
        notificationManager.notify(notificationIdUtils.getNotificationId(), mBuilder.build())
    }
}