package ru.nbsp.pushka.ui.login

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.TextView
import com.flaviofaria.kenburnsview.KenBurnsView
import com.flaviofaria.kenburnsview.RandomTransitionGenerator
import ru.nbsp.pushka.BaseApplication
import ru.nbsp.pushka.R
import ru.nbsp.pushka.auth.SocialAuthListener
import ru.nbsp.pushka.auth.SocialAuthManager
import ru.nbsp.pushka.dependency.bindView
import ru.nbsp.pushka.mvp.PresentedActivity
import ru.nbsp.pushka.mvp.presenters.login.LoginPresenter
import ru.nbsp.pushka.mvp.views.login.LoginView
import javax.inject.Inject

/**
 * Created by Dimorinny on 16.02.16.
 */
class LoginActivity : PresentedActivity<LoginPresenter>(), LoginView, SocialAuthListener {

    companion object {
        private const val KEN_BURNS_DURATION = 20000L;
        private const val FONT_PATH = "fonts/Bebas.ttf"
    }

    @Inject
    lateinit var presenter: LoginPresenter

    lateinit var socialAuthManager: SocialAuthManager

    val textLogo: TextView by bindView(R.id.text_logo)
    val backgroundImage: KenBurnsView by bindView(R.id.login_background_image)
    val vkButton: View by bindView(R.id.login_vk_button)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        BaseApplication.graph.inject(this)

        initPresenter(presenter)
        initFont()
        initKenBurns()
        initAuthManager()
        initViews()
    }

    private fun initViews() {
        vkButton.setOnClickListener { presenter.onVkLoginButtonClicked() }
    }

    private fun initAuthManager() {
        socialAuthManager = SocialAuthManager(this)
    }

    override fun initPresenter(presenter: LoginPresenter) {
        presenter.view = this
        super.initPresenter(presenter)
    }

    private fun initKenBurns() {
        backgroundImage.setTransitionGenerator(
                RandomTransitionGenerator(KEN_BURNS_DURATION,
                        AccelerateDecelerateInterpolator()))
    }

    private fun initFont() {
        textLogo.typeface = Typeface.createFromAsset(assets, FONT_PATH)
    }

    override fun openVkLoginDialog() {
        socialAuthManager.login(SocialAuthManager.DRIVER_VK, this)
    }

    override fun onSocialLoginSuccess(provider: String, token: String) {
        Log.v("LoginActivity", provider)
        Log.v("LoginActivity", token)
        presenter.onLoginSuccess(provider, token)
    }

    override fun onSocialLoginError() {
        Log.v("LoginActivity", "Error")
        presenter.onLoginError()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        socialAuthManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }
}