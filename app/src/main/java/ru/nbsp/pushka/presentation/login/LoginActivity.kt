package ru.nbsp.pushka.presentation.login

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.TextView
import com.flaviofaria.kenburnsview.KenBurnsView
import com.flaviofaria.kenburnsview.RandomTransitionGenerator
import ru.nbsp.pushka.BaseApplication
import ru.nbsp.pushka.R
import ru.nbsp.pushka.network.auth.social.SocialAuthListener
import ru.nbsp.pushka.network.auth.social.SocialAuthManager
import ru.nbsp.pushka.presentation.PresentedActivity
import ru.nbsp.pushka.presentation.alert.feed.AlertsActivity
import ru.nbsp.pushka.util.AlertUtils
import ru.nbsp.pushka.util.bindView
import javax.inject.Inject

/**
 * Created by Dimorinny on 16.02.16.
 */
class LoginActivity : PresentedActivity<LoginPresenter>(), LoginView, SocialAuthListener {

    companion object {
        private const val KEN_BURNS_DURATION = 20000L;
        private const val FONT_PATH = "fonts/Bebas.ttf"

        private const val HELP_URL = "https://pushka.xyz/"
    }

    @Inject
    lateinit var presenter: LoginPresenter

    @Inject
    lateinit var alertUtils: AlertUtils

    lateinit var socialAuthManager: SocialAuthManager

    val progressDialog: ProgressDialog by lazy { ProgressDialog(this) }
    val textLogo: TextView by bindView(R.id.text_logo)
    val backgroundImage: KenBurnsView by bindView(R.id.login_background_image)
    val vkButton: View by bindView(R.id.login_vk_button)
    val googleButton: View by bindView(R.id.login_google_button)
    val helpButton: View by bindView(R.id.login_help_button)

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
        progressDialog.setMessage(resources.getString(R.string.login_dialog_message))
        vkButton.setOnClickListener { presenter.onVkLoginButtonClicked() }
        googleButton.setOnClickListener { presenter.onGoogleLoginButtonClicked() }
        helpButton.setOnClickListener { presenter.onHelpButtonClicked() }
    }

    private fun initAuthManager() {
        socialAuthManager = SocialAuthManager(this)
        socialAuthManager.initGoogleAuth(this)
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

    override fun openHelp() {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(HELP_URL))
        startActivity(browserIntent)
    }

    override fun openGoogleLoginDialog() {
        socialAuthManager.login(SocialAuthManager.DRIVER_GOOGLE, this)
    }

    override fun onSocialLoginSuccess(provider: String, token: String) {
        presenter.onLoginSuccess(provider, token)
    }

    override fun onSocialLoginError() {
        presenter.onLoginError()
    }

    override fun showAlert(message: String) {
        alertUtils.showAlert(this, message)
    }

    override fun showDialog() {
        progressDialog.show()
    }

    override fun hideDialog() {
        progressDialog.dismiss()
    }

    override fun openNavigationWindow() {
        val intent = Intent(this, AlertsActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        socialAuthManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        super.onDestroy()

        if (progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }
}