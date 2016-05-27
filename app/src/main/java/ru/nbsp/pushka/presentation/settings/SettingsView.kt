package ru.nbsp.pushka.presentation.settings

import ru.nbsp.pushka.presentation.core.base.BaseView

/**
 * Created by Dimorinny on 23.05.16.
 */
interface SettingsView : BaseView {
    fun showLogoutDialog()
    fun hideLogoutDialog()
    fun showLogoutProgressDialog()
    fun hideLogoutProgressDialog()
    fun openLoginScreen()
    fun showLogoutConnectionError(message: String)
}