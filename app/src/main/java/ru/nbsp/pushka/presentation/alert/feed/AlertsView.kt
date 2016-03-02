package ru.nbsp.pushka.presentation.alert.feed

import ru.nbsp.pushka.data.entity.Alert
import ru.nbsp.pushka.presentation.core.base.BaseView

/**
 * Created by Dimorinny on 24.02.16.
 */
interface AlertsView : BaseView {
    fun setAlerts(alerts: List<Alert>)
    fun setErrorState()
    fun openUrl(url: String)
}