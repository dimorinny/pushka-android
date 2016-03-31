package ru.nbsp.pushka.presentation.device.feed

import ru.nbsp.pushka.presentation.core.base.BaseView
import ru.nbsp.pushka.presentation.core.model.device.PresentationDevice
import ru.nbsp.pushka.presentation.core.state.State

/**
 * Created by Dimorinny on 31.03.16.
 */
interface DevicesView : BaseView {
    fun setState(state: State)
    fun setDevices(devices: List<PresentationDevice>)
}