package ru.nbsp.pushka.service.api

import android.app.Service
import android.content.Intent
import android.os.IBinder
import ru.nbsp.pushka.BaseApplication
import ru.nbsp.pushka.annotation.ApiRepository
import ru.nbsp.pushka.bus.RxBus
import ru.nbsp.pushka.bus.event.BaseEvent
import ru.nbsp.pushka.bus.event.subscription.LoadSourceAndSubscriptionEvent
import ru.nbsp.pushka.bus.event.subscription.LoadSubscriptionEvent
import ru.nbsp.pushka.bus.event.subscription.LoadSubscriptionsEvent
import ru.nbsp.pushka.bus.event.subscription.SubscribeEvent
import ru.nbsp.pushka.interactor.source.StorageSourceInteractor
import ru.nbsp.pushka.interactor.subscription.ApiSubscriptionInteractor
import ru.nbsp.pushka.interactor.subscription.StorageSubscriptionInteractor
import ru.nbsp.pushka.network.request.SubscribeRequest
import ru.nbsp.pushka.repository.source.SourcesRepository
import ru.nbsp.pushka.repository.subscription.SubscriptionRepository
import ru.nbsp.pushka.service.BaseEventSubscriber
import rx.subscriptions.CompositeSubscription
import java.util.*
import javax.inject.Inject

/**
 * Created by Dimorinny on 05.04.16.
 */
class ApiSubscriptionService : Service() {

    @Inject
    lateinit var bus: RxBus

    @field:[Inject ApiRepository]
    lateinit var apiSubscriptionRepository: SubscriptionRepository

    @field:[Inject ApiRepository]
    lateinit var apiSourceRepository: SourcesRepository

    @Inject
    lateinit var storageSourceInteractor: StorageSourceInteractor

    @Inject
    lateinit var storageSubscriptionInteractor: StorageSubscriptionInteractor

    @Inject
    lateinit var apiSubscriptionInteractor: ApiSubscriptionInteractor

    private val subscription = CompositeSubscription()

    companion object {
        const val ARG_SERVICE_COMMAND = "arg_service_command"
        const val ARG_SOURCE_ID = "arg_source_id"
        const val ARG_SOURCE_PARAMS = "arg_source_params"
        const val ARG_SUBSCRIPTION_ID = "arg_subscription_id"
        const val COMMAND_LOAD_SUBSCRIPTIONS = "command_load_subscriptions"
        const val COMMAND_LOAD_SOURCE_AND_SUBSCRIPTION = "command_load_source_and_subscription"
        const val COMMAND_LOAD_SUBSCRIPTION = "command_load_subscription"
        const val COMMAND_SUBSCRIBE = "command_subscribe"
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
            COMMAND_LOAD_SUBSCRIPTION -> {
                handleLoadSubscriptionCommand(intent, startId)
            }
            COMMAND_LOAD_SOURCE_AND_SUBSCRIPTION -> {
                handleLoadSourceAndSubscriptionCommand(intent, startId)
            }
            COMMAND_SUBSCRIBE -> {
                handleSubscribeCommand(intent, startId)
            }
        }

        return Service.START_NOT_STICKY
    }

    @Suppress("UNCHECKED_CAST")
    private fun handleSubscribeCommand(intent: Intent, startId: Int) {
        val sourceId = intent.getStringExtra(ARG_SOURCE_ID)
        val params = intent.getSerializableExtra(ARG_SOURCE_PARAMS) as HashMap<String, String?>

        apiSubscriptionInteractor.subscribe(SubscribeRequest(sourceId, params))
                .flatMap { storageSubscriptionInteractor.saveSubscription(it) }
                .subscribe(object : BaseEventSubscriber(this, startId, bus) {
                    override fun error(t: Throwable): BaseEvent {
                        return SubscribeEvent.Error(t)
                    }

                    override fun success(): BaseEvent {
                        return SubscribeEvent.Success()
                    }
                })
    }

    private fun handleLoadSubscriptionCommand(intent: Intent, startId: Int) {
        val subscriptionId = intent.getStringExtra(ARG_SUBSCRIPTION_ID)

        apiSubscriptionRepository.getSubscription(subscriptionId)
                .flatMap { storageSubscriptionInteractor.saveSubscription(it) }
                .subscribe(object  : BaseEventSubscriber(this, startId, bus) {
                    override fun error(t: Throwable): BaseEvent {
                        return LoadSubscriptionEvent.Error(t)
                    }

                    override fun success(): BaseEvent {
                        return LoadSubscriptionEvent.Success(subscriptionId)
                    }
                })
    }

    private fun handleLoadSourceAndSubscriptionCommand(intent: Intent, startId: Int) {
        val sourceId = intent.getStringExtra(ARG_SOURCE_ID)
        val subscriptionId = intent.getStringExtra(ARG_SUBSCRIPTION_ID)

        apiSourceRepository.getSource(sourceId)
                .flatMap { storageSourceInteractor.saveSource(it) }
                .flatMap { apiSubscriptionRepository.getSubscription(subscriptionId) }
                .flatMap { storageSubscriptionInteractor.saveSubscription(it) }
                .subscribe(object : BaseEventSubscriber(this, startId, bus) {
                    override fun error(t: Throwable): BaseEvent {
                        return LoadSourceAndSubscriptionEvent.Error(t)
                    }

                    override fun success(): BaseEvent {
                        return LoadSourceAndSubscriptionEvent.Success(sourceId, subscriptionId)
                    }
                })
    }

    private fun handleLoadSubscriptionsCommand(startId: Int) {
        apiSubscriptionRepository.getSubscriptions()
                .flatMap {
                    storageSubscriptionInteractor.saveSubscriptions(it)
                }
                .subscribe(object : BaseEventSubscriber(this, startId, bus) {
                    override fun error(t: Throwable): BaseEvent {
                        return LoadSubscriptionsEvent.Error(t)
                    }

                    override fun success(): BaseEvent {
                        return LoadSubscriptionsEvent.Success()
                    }
                })
    }
}