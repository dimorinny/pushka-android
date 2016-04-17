package ru.nbsp.pushka.presentation.device.feed.container

import ru.nbsp.pushka.presentation.core.base.BaseView
import ru.nbsp.pushka.presentation.core.model.device.PresentationDeviceType

/**
 * Created by Dimorinny on 18.04.16.
 */
interface DevicesContainerView : BaseView {
    fun openUrl(url: String)
    fun setDeviceTypes(deviceTypes: List<PresentationDeviceType>)
}