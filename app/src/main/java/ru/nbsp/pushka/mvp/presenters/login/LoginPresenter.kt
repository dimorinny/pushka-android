package ru.nbsp.pushka.mvp.presenters.login

import ru.nbsp.pushka.mvp.presenters.BasePresenter
import ru.nbsp.pushka.mvp.views.login.LoginView
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Dimorinny on 16.02.16.
 */
@Singleton
class LoginPresenter @Inject constructor(): BasePresenter {
    override var view: LoginView? = null
}