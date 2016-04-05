package ru.nbsp.pushka.service

import android.content.Context
import android.content.Intent
import ru.nbsp.pushka.service.api.ApiAlertService
import ru.nbsp.pushka.service.api.ApiAuthService
import ru.nbsp.pushka.service.api.ApiSourceService
import ru.nbsp.pushka.service.api.ApiSubscriptionService
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
}