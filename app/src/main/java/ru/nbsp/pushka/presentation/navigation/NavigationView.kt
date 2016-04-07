package ru.nbsp.pushka.presentation.navigation

import ru.nbsp.pushka.network.auth.Account
import ru.nbsp.pushka.presentation.core.base.BaseView
import ru.nbsp.pushka.presentation.core.model.device.PresentationDeviceType

/**
 * Created by Dimorinny on 22.02.16.
 */
interface NavigationView : BaseView {
    fun setFeedContent()
    fun setSettingsContent()
    fun setSourcesContent()
    fun setSubscriptionsContent()
    fun setCategoriesContent()
    fun setDevicesContent()
    fun openUrl(url: String)
    fun setDeviceTypes(deviceTypes: List<PresentationDeviceType>)
    fun setAccount(account: Account)
}