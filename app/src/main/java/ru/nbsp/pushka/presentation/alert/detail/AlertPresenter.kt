package ru.nbsp.pushka.presentation.alert.detail

import ru.nbsp.pushka.presentation.core.base.BasePresenter
import javax.inject.Inject

/**
 * Created by Dimorinny on 16.03.16.
 */
class AlertPresenter
    @Inject constructor() : BasePresenter {

    override var view: AlertView? = null
}