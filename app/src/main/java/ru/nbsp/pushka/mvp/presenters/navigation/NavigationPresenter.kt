package ru.nbsp.pushka.mvp.presenters.navigation

import ru.nbsp.pushka.auth.AccountManager
import ru.nbsp.pushka.mvp.presenters.BasePresenter
import ru.nbsp.pushka.mvp.views.navigation.NavigationView
import ru.nbsp.pushka.ui.navigation.drawer.NavigationDrawerItem
import javax.inject.Inject

/**
 * Created by Dimorinny on 22.02.16.
 */
class NavigationPresenter
    @Inject constructor(val accountManager: AccountManager) : BasePresenter {

    override var view: NavigationView? = null

    fun onDrawerItemClicked(drawerItem: NavigationDrawerItem): Boolean {
        return when (drawerItem) {
            NavigationDrawerItem.FEED -> { view?.setFeedContent(); true }
            NavigationDrawerItem.EXIT -> { view?.openExitDialog(); true }
            else -> false
        }
    }

    fun loadAccount() {
        view?.setAccount(accountManager.getAccount()!!)
    }

    fun onExitDialogPositiveClicked() {
        accountManager.clear()
        view?.openLoginActivity()
    }
}