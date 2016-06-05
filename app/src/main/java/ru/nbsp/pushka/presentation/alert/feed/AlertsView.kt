package ru.nbsp.pushka.presentation.alert.feed

import ru.nbsp.pushka.presentation.core.base.BaseView
import ru.nbsp.pushka.presentation.core.model.alert.PresentationAlert
import ru.nbsp.pushka.presentation.core.state.State

/**
 * Created by Dimorinny on 24.02.16.
 */
interface AlertsView : BaseView {
    fun setAlerts(alerts: List<PresentationAlert>)
    fun setState(state: State)
    fun openAlertScreen(alert: PresentationAlert)
    fun disableSwipeRefresh()
    fun showMessage(message: String)
    fun showLoadConnectionAlertsError(message: String)
}