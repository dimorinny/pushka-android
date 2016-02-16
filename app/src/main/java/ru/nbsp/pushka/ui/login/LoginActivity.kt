package ru.nbsp.pushka.ui.login

import android.os.Bundle
import ru.nbsp.pushka.BaseApplication
import ru.nbsp.pushka.R
import ru.nbsp.pushka.mvp.PresentedActivity
import ru.nbsp.pushka.mvp.presenters.login.LoginPresenter
import ru.nbsp.pushka.mvp.views.login.LoginView
import javax.inject.Inject

/**
 * Created by Dimorinny on 16.02.16.
 */
class LoginActivity : PresentedActivity<LoginPresenter>(), LoginView {

    @Inject
    lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        BaseApplication.graph.inject(this)
        presenter.view = this
        initPresenter(presenter)
    }
}