package ru.nbsp.pushka.presentation.navigation

import ru.nbsp.pushka.auth.Account
import ru.nbsp.pushka.presentation.BaseView

/**
 * Created by Dimorinny on 22.02.16.
 */
interface NavigationView : BaseView {
    fun setFeedContent()
    fun setAccount(account: Account)
    fun openLoginActivity()
    fun openExitDialog()
}