package ru.nbsp.pushka.service

import android.content.Context
import android.content.Intent
import ru.nbsp.pushka.service.api.*
import ru.nbsp.pushka.service.application.DataService
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Dimorinny on 12.02.16.
 */
@Singleton
class ServiceManager
    @Inject constructor(val context: Context) {

    fun login(provider: String, token: String) {
        val intent = Intent(context, ApiAuthService::class.java)
        intent.putExtra(ApiAuthService.ARG_SERVICE_COMMAND, ApiAuthService.COMMAND_LOGIN)
        intent.putExtra(ApiAuthService.ARG_LOGIN_TOKEN, token)
        intent.putExtra(ApiAuthService.ARG_LOGIN_PROVIDER, provider)
        context.startService(intent)
    }

    fun loadAlerts() {
        val intent = Intent(context, ApiAlertService::class.java)
        intent.putExtra(ApiAlertService.ARG_SERVICE_COMMAND, ApiAlertService.COMMAND_LOAD_ALERTS)
        context.startService(intent)
    }

    fun loadMoreAlerts(firstItemTime: Long, offset: Int) {
        val intent = Intent(context, ApiAlertService::class.java)
        intent.putExtra(ApiAlertService.ARG_SERVICE_COMMAND, ApiAlertService.COMMAND_LOAD_MORE_ALERTS)
        intent.putExtra(ApiAlertService.ARG_FIRST_ITEM_TIME, firstItemTime)
        intent.putExtra(ApiAlertService.ARG_OFFSET, offset)
        context.startService(intent)
    }

    fun loadAlert(alertId: String) {
        val intent = Intent(context, ApiAlertService::class.java)
        intent.putExtra(ApiAlertService.ARG_SERVICE_COMMAND, ApiAlertService.COMMAND_LOAD_ALERT)
        intent.putExtra(ApiAlertService.ARG_ALERT_ID, alertId)
        context.startService(intent)
    }

    fun loadSources(categoryId: String) {
        val intent = Intent(context, ApiSourceService::class.java)
        intent.putExtra(ApiSourceService.ARG_SERVICE_COMMAND, ApiSourceService.COMMAND_LOAD_SOURCES)
        intent.putExtra(ApiSourceService.ARG_CATEGORY_ID, categoryId)
        context.startService(intent)
    }

    fun loadSource(sourceId: String) {
        val intent = Intent(context, ApiSourceService::class.java)
        intent.putExtra(ApiSourceService.ARG_SERVICE_COMMAND, ApiSourceService.COMMAND_LOAD_SOURCE)
        intent.putExtra(ApiSourceService.ARG_SOURCE_ID, sourceId)
        context.startService(intent)
    }

    fun loadCategories() {
        val intent = Intent(context, ApiSourceService::class.java)
        intent.putExtra(ApiSourceService.ARG_SERVICE_COMMAND, ApiSourceService.COMMAND_LOAD_CATEGORIES)
        context.startService(intent)
    }

    fun loadSubscriptions() {
        val intent = Intent(context, ApiSubscriptionService::class.java)
        intent.putExtra(ApiSubscriptionService.ARG_SERVICE_COMMAND, ApiSubscriptionService.COMMAND_LOAD_SUBSCRIPTIONS)
        context.startService(intent)
    }

    fun loadSubscription(subscriptionId: String) {
        val intent = Intent(context, ApiSubscriptionService::class.java)
        intent.putExtra(ApiSubscriptionService.ARG_SERVICE_COMMAND, ApiSubscriptionService.COMMAND_LOAD_SUBSCRIPTION)
        intent.putExtra(ApiSubscriptionService.ARG_SUBSCRIPTION_ID, subscriptionId)
        context.startService(intent)
    }

    fun loadSourceAndSubscription(sourceId: String, subscriptionId: String) {
        val intent = Intent(context, ApiSubscriptionService::class.java)
        intent.putExtra(ApiSubscriptionService.ARG_SERVICE_COMMAND, ApiSubscriptionService.COMMAND_LOAD_SOURCE_AND_SUBSCRIPTION)
        intent.putExtra(ApiSubscriptionService.ARG_SOURCE_ID, sourceId)
        intent.putExtra(ApiSubscriptionService.ARG_SUBSCRIPTION_ID, subscriptionId)
        context.startService(intent)
    }

    fun subscribe(sourceId: String, sound: Boolean, notification: Boolean, params: HashMap<String, String?>) {
        val intent = Intent(context, ApiSubscriptionService::class.java)
        intent.putExtra(ApiSubscriptionService.ARG_SERVICE_COMMAND, ApiSubscriptionService.COMMAND_SUBSCRIBE)
        intent.putExtra(ApiSubscriptionService.ARG_SOURCE_ID, sourceId)
        intent.putExtra(ApiSubscriptionService.ARG_SOUND, sound)
        intent.putExtra(ApiSubscriptionService.ARG_NOTIFICATION, notification)
        intent.putExtra(ApiSubscriptionService.ARG_SOURCE_PARAMS, params)
        context.startService(intent)
    }

    fun unsubscribe(subscriptionId: String) {
        val intent = Intent(context, ApiSubscriptionService::class.java)
        intent.putExtra(ApiSubscriptionService.ARG_SERVICE_COMMAND, ApiSubscriptionService.COMMAND_UNSUBSCRIBE)
        intent.putExtra(ApiSubscriptionService.ARG_SUBSCRIPTION_ID, subscriptionId)
        context.startService(intent)
    }

    fun loadDevices() {
        val intent = Intent(context, ApiDeviceService::class.java)
        intent.putExtra(ApiDeviceService.ARG_SERVICE_COMMAND, ApiDeviceService.COMMAND_LOAD_DEVICES)
        context.startService(intent)
    }

    fun removeGcmDevice() {
        val intent = Intent(context, ApiDeviceService::class.java)
        intent.putExtra(ApiDeviceService.ARG_SERVICE_COMMAND, ApiDeviceService.COMMAND_REMOVE_DEVICE)
        context.startService(intent)
    }

    fun clearData() {
        val intent = Intent(context, DataService::class.java)
        intent.putExtra(DataService.ARG_SERVICE_COMMAND, DataService.COMMAND_CLEAR_ALL)
        context.startService(intent)
    }
}