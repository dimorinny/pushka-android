package ru.nbsp.pushka.presentation.navigation

import ru.nbsp.pushka.R
import ru.nbsp.pushka.network.auth.AccountManager
import ru.nbsp.pushka.presentation.core.base.BasePresenter
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
            R.id.drawer_sources -> view?.setCategoriesContent()
            R.id.drawer_subscriptions -> view?.setSubscriptionsContent()
            R.id.drawer_settings -> view?.setSettingsContent()
        }
    }

    fun loadAccount() {
        view?.setAccount(accountManager.getAccount()!!)
    }
}