package ru.nbsp.pushka.presentation.device.feed.container

import ru.nbsp.pushka.network.auth.Account
import ru.nbsp.pushka.network.auth.AccountManager
import ru.nbsp.pushka.presentation.core.base.BasePresenter
import ru.nbsp.pushka.presentation.core.model.device.PresentationDeviceType
import ru.nbsp.pushka.util.TelegramUtils
import javax.inject.Inject

/**
 * Created by Dimorinny on 18.04.16.
 */

class DevicesContainerPresenter
    @Inject constructor(val accountManager: AccountManager,
                        val telegramUtils: TelegramUtils) : BasePresenter {

    companion object {
        val ALLOW_DEVICE_TYPES = listOf(PresentationDeviceType.Telegram())
    }

    lateinit var account: Account
    override var view: DevicesContainerView? = null

    override fun onCreate() {
        super.onCreate()

        account = accountManager.getAccount()!!
        view?.setDeviceTypes(ALLOW_DEVICE_TYPES)
    }

    fun onCreateDeviceItemClicked(deviceType: PresentationDeviceType) {
        when (deviceType) {
            is PresentationDeviceType.Telegram -> handleAddTelegramDevice()
        }
    }

    private fun handleAddTelegramDevice() {
        view?.openUrl(telegramUtils.generateSubscribeUrl(account.userId))
    }
}