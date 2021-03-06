package ru.nbsp.pushka.presentation.navigation

import ru.nbsp.pushka.network.auth.Account
import ru.nbsp.pushka.presentation.core.base.BaseView

/**
 * Created by Dimorinny on 22.02.16.
 */
interface NavigationView : BaseView {
    fun setFeedContent()
    fun setSettingsContent()
    fun setSubscriptionsContent()
    fun setCategoriesContent()
    fun setDevicesContent()
    fun setAccount(account: Account)
}