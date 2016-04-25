package ru.nbsp.pushka.interactor.app

import android.content.Context
import android.content.Intent
import ru.nbsp.pushka.presentation.login.LoginActivity
import ru.nbsp.pushka.service.ServiceManager

/**
 * Created by Dimorinny on 26.04.16.
 */
class ApplicationInteractorImpl(val context: Context,
                                val serviceManager: ServiceManager) : ApplicationInteractor {

    override fun logout() {
        serviceManager.clearData()
        openLoginActivity()
    }

    private fun openLoginActivity() {
        val intent = Intent(context, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
    }
}