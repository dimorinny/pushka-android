package ru.nbsp.pushka.ui.login

import android.graphics.Typeface
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.TextView
import com.dimorinny.vscale.dependency.bindView
import com.flaviofaria.kenburnsview.KenBurnsView
import com.flaviofaria.kenburnsview.RandomTransitionGenerator
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

    companion object {
        private const val KEN_BURNS_DURATION = 20000L;
        private const val FONT_PATH = "fonts/Bebas.ttf"
    }

    @Inject
    lateinit var presenter: LoginPresenter

    val textLogo: TextView by bindView(R.id.text_logo)
    val backgroundImage: KenBurnsView by bindView(R.id.login_background_image)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        BaseApplication.graph.inject(this)
        presenter.view = this
        initPresenter(presenter)

        initFont()
        initKenBurns()
    }

    private fun initKenBurns() {
        backgroundImage.setTransitionGenerator(
                RandomTransitionGenerator(KEN_BURNS_DURATION,
                        AccelerateDecelerateInterpolator()))
    }

    private fun initFont() {
        textLogo.typeface = Typeface.createFromAsset(assets, FONT_PATH)
    }
}