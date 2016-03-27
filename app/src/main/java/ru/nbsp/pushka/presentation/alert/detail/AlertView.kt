package ru.nbsp.pushka.presentation.alert.detail

import ru.nbsp.pushka.presentation.core.base.BaseView
import ru.nbsp.pushka.presentation.core.model.alert.PresentationAction

/**
 * Created by Dimorinny on 16.03.16.
 */
interface AlertView : BaseView {
    fun setContentUrl(url: String)
    fun setTitle(alertTitle: String)
    fun setActions(actions: List<PresentationAction>)
}