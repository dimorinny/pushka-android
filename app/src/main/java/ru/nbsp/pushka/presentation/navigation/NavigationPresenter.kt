package ru.nbsp.pushka.presentation.navigation

import ru.nbsp.pushka.R
import ru.nbsp.pushka.network.auth.Account
import ru.nbsp.pushka.network.auth.AccountManager
import ru.nbsp.pushka.presentation.core.base.BasePresenter
import ru.nbsp.pushka.presentation.core.model.device.PresentationDeviceType
import ru.nbsp.pushka.util.TelegramUtils
import javax.inject.Inject

/**
 * Created by Dimorinny on 22.02.16.
 */
class NavigationPresenter
    @Inject constructor(val accountManager: AccountManager,
                        val telegramUtils: TelegramUtils) : BasePresenter {

    companion object {
        val ALLOW_DEVICE_TYPES = listOf(PresentationDeviceType.Telegram())
    }

    override var view: NavigationView? = null
    lateinit var account: Account

    override fun onCreate() {
        super.onCreate()
        view?.setDeviceTypes(ALLOW_DEVICE_TYPES)
        account = accountManager.getAccount()!!
    }

    fun onDrawerItemClicked(drawerItem: Int) {
        when (drawerItem) {
            R.id.drawer_feed -> view?.setFeedContent()
            R.id.drawer_sources -> view?.setCategoriesContent()
            R.id.drawer_subscriptions -> view?.setSubscriptionsContent()
            R.id.drawer_settings -> view?.setSettingsContent()
            R.id.drawer_devices -> view?.setDevicesContent()
        }
    }

    fun onCreateDeviceItemClicked(deviceType: PresentationDeviceType) {
        when (deviceType) {
            is PresentationDeviceType.Telegram -> handleAddTelegramDevice()
        }
    }

    private fun handleAddTelegramDevice() {
        view?.openUrl(telegramUtils.generateSubscribeUrl(account.userId))
    }

    fun loadAccount() {
        view?.setAccount(account)
    }
}