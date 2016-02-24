package ru.nbsp.pushka.presentation.login

import ru.nbsp.pushka.presentation.core.base.BaseView

/**
 * Created by Dimorinny on 16.02.16.
 */
interface LoginView : BaseView {
    fun openVkLoginDialog()
    fun openGoogleLoginDialog()
    fun showAlert(message: String)
    fun showDialog()
    fun hideDialog()
    fun openNavigationWindow()
}