package ru.nbsp.pushka.service.api

import android.app.Service
import android.content.Intent
import android.os.IBinder
import ru.nbsp.pushka.BaseApplication
import ru.nbsp.pushka.annotation.ApiRepository
import ru.nbsp.pushka.bus.RxBus
import ru.nbsp.pushka.bus.event.subscription.LoadSubscriptionsEvent
import ru.nbsp.pushka.interactor.subscription.StorageSubscriptionInteractor
import ru.nbsp.pushka.presentation.core.model.subscription.PresentationSubscription
import ru.nbsp.pushka.repository.subscription.SubscriptionRepository
import rx.Subscriber
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

/**
 * Created by Dimorinny on 05.04.16.
 */
class ApiSubscriptionService : Service() {

    @Inject
    lateinit var bus: RxBus

    @field:[Inject ApiRepository]
    lateinit var apiSubscriptionRepository: SubscriptionRepository

    @Inject
    lateinit var storageSubscriptionInteractor: StorageSubscriptionInteractor

    private val subscription = CompositeSubscription()

    companion object {
        const val ARG_SERVICE_COMMAND = "arg_service_command"
        const val COMMAND_LOAD_SUBSCRIPTIONS = "command_load_subscriptions"
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
            return Service.START_NOT_STICKY
        }

        val command = intent.getStringExtra(ARG_SERVICE_COMMAND)

        when (command) {
            COMMAND_LOAD_SUBSCRIPTIONS -> {
                handleLoadSubscriptionsCommand(startId)
            }
        }

        return Service.START_NOT_STICKY
    }

    private fun handleLoadSubscriptionsCommand(startId: Int) {
        apiSubscriptionRepository.getSubscriptions()
                .flatMap {
                    storageSubscriptionInteractor.saveSubscriptions(it)
                }
                .subscribe(LoadSubscriptionsSubscriber(startId))
    }

    inner class LoadSubscriptionsSubscriber(val startId: Int): Subscriber<List<PresentationSubscription>>() {
        override fun onCompleted() {}

        override fun onError(t: Throwable) {
            bus.post(LoadSubscriptionsEvent.Error(t) as LoadSubscriptionsEvent)
            stopSelf(startId)
        }

        override fun onNext(subscriptions: List<PresentationSubscription>) {
            bus.post(LoadSubscriptionsEvent.Success() as LoadSubscriptionsEvent)
            stopSelf(startId)
        }
    }
}