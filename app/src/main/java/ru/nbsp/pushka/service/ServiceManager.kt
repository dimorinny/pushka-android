package ru.nbsp.pushka.service

import android.content.Context
import android.content.Intent
import ru.nbsp.pushka.service.api.ApiPushkaService
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Dimorinny on 12.02.16.
 */
@Singleton
class ServiceManager
    @Inject constructor(val context: Context) {

    fun login(provider: String, token: String) {
        val intent = Intent(context, ApiPushkaService::class.java)
        intent.putExtra(ApiPushkaService.ARG_SERVICE_COMMAND, ApiPushkaService.COMMAND_LOGIN)
        intent.putExtra(ApiPushkaService.ARG_LOGIN_TOKEN, token)
        intent.putExtra(ApiPushkaService.ARG_LOGIN_PROVIDER, provider)
        context.startService(intent)
    }

    fun loadAlerts() {
        val intent = Intent(context, ApiPushkaService::class.java)
        intent.putExtra(ApiPushkaService.ARG_SERVICE_COMMAND, ApiPushkaService.COMMAND_LOAD_ALERTS)
        context.startService(intent)
    }

    fun loadSources(categoryId: String) {
        val intent = Intent(context, ApiPushkaService::class.java)
        intent.putExtra(ApiPushkaService.ARG_SERVICE_COMMAND, ApiPushkaService.COMMAND_LOAD_SOURCES)
        intent.putExtra(ApiPushkaService.ARG_CATEGORY_ID, categoryId)
        context.startService(intent)
    }

    fun loadCategories() {
        val intent = Intent(context, ApiPushkaService::class.java)
        intent.putExtra(ApiPushkaService.ARG_SERVICE_COMMAND, ApiPushkaService.COMMAND_LOAD_CATEGORIES)
        context.startService(intent)
    }
}