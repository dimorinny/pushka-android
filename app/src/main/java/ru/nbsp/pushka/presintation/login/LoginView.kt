package ru.nbsp.pushka.presintation.login

import ru.nbsp.pushka.presintation.BaseView

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