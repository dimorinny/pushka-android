package ru.nbsp.pushka.mvp.presenters.login

import ru.nbsp.pushka.mvp.presenters.BasePresenter
import ru.nbsp.pushka.mvp.views.login.LoginView
import javax.inject.Inject

/**
 * Created by Dimorinny on 16.02.16.
 */

class LoginPresenter @Inject constructor(): BasePresenter {
    override var view: LoginView? = null

    fun onVkLoginButtonClicked() {
        view?.openVkLoginDialog()
    }

    fun onGoogleLoginButtonClicked() {
    }
}