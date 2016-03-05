package ru.nbsp.pushka.presentation.alert.feed

import ru.nbsp.pushka.data.model.alert.Alert
import ru.nbsp.pushka.presentation.core.base.BaseView
import ru.nbsp.pushka.presentation.core.state.State

/**
 * Created by Dimorinny on 24.02.16.
 */
interface AlertsView : BaseView {
    fun setAlerts(alerts: List<Alert>)
    fun setState(state: State)
    fun setToolbarState(state: State)
    fun openUrl(url: String)
}