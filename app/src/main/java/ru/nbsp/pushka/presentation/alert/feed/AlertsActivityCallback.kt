package ru.nbsp.pushka.presentation.alert.feed

/**
 * Created by Dimorinny on 06.06.16.
 */
interface AlertsActivityCallback {
    fun showMessage(message: String)
    fun showLoadAlertsConnectionError(message: String)
}