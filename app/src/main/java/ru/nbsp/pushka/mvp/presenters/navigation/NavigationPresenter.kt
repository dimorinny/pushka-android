package ru.nbsp.pushka.mvp.presenters.navigation

import ru.nbsp.pushka.R
import ru.nbsp.pushka.auth.AccountManager
import ru.nbsp.pushka.mvp.presenters.BasePresenter
import ru.nbsp.pushka.mvp.views.navigation.NavigationView
import javax.inject.Inject

/**
 * Created by Dimorinny on 22.02.16.
 */
class NavigationPresenter
    @Inject constructor(val accountManager: AccountManager) : BasePresenter {

    override var view: NavigationView? = null

    fun onDrawerItemClicked(drawerItem: Int) {
        when (drawerItem) {
            R.id.drawer_feed -> view?.setFeedContent()
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