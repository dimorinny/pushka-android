package ru.nbsp.pushka.presintation.navigation

import ru.nbsp.pushka.auth.Account
import ru.nbsp.pushka.presintation.BaseView

/**
 * Created by Dimorinny on 22.02.16.
 */
interface NavigationView : BaseView {
    fun setFeedContent()
    fun setAccount(account: Account)
    fun openLoginActivity()
    fun openExitDialog()
}