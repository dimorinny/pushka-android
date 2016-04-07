package ru.nbsp.pushka.presentation.navigation

import ru.nbsp.pushka.R
import ru.nbsp.pushka.network.auth.AccountManager
import ru.nbsp.pushka.presentation.core.base.BasePresenter
import ru.nbsp.pushka.presentation.core.model.device.PresentationDeviceType
import javax.inject.Inject

/**
 * Created by Dimorinny on 22.02.16.
 */
class NavigationPresenter
    @Inject constructor(val accountManager: AccountManager) : BasePresenter {

    companion object {
        val ALLOW_DEVICE_TYPES = listOf(PresentationDeviceType.Telegram())
    }

    override var view: NavigationView? = null

    override fun onCreate() {
        super.onCreate()
        view?.setDeviceTypes(ALLOW_DEVICE_TYPES)
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
        // TODO: hardcode
        view?.openUrl("https://telegram.me/pushkabot?start=vk201443862")
    }

    fun loadAccount() {
        view?.setAccount(accountManager.getAccount()!!)
    }
}