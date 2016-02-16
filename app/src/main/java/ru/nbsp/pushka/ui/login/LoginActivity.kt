package ru.nbsp.pushka.ui.login

import android.graphics.Typeface
import android.os.Bundle
import android.widget.TextView
import com.dimorinny.vscale.dependency.bindView
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

    val textLogo: TextView by bindView(R.id.text_logo)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        BaseApplication.graph.inject(this)
        presenter.view = this
        initPresenter(presenter)

        initFont()
    }

    private fun initFont() {
        textLogo.typeface = Typeface.createFromAsset(assets, "fonts/Bebas.ttf");
    }
}