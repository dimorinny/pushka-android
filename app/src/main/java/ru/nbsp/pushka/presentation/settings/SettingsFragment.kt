package ru.nbsp.pushka.presentation.settings

import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import com.github.machinarius.preferencefragment.PreferenceFragment
import ru.nbsp.pushka.BaseApplication
import ru.nbsp.pushka.R
import ru.nbsp.pushka.presentation.login.LoginActivity
import javax.inject.Inject

/**
 * Created by Dimorinny on 24.02.16.
 */
class SettingsFragment : PreferenceFragment(), SettingsView {

    lateinit var logoutDialog: AlertDialog
    var settingsActivityCallback: SettingsActivityCallback? = null

    val logoutProgressDialog: ProgressDialog by lazy { ProgressDialog(activity) }

    @Inject
    lateinit var settingsPresenter: SettingsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.preferences)
        BaseApplication.graph.inject(this)
        initPresenter()
        initDialog()
        initViews()
    }

    fun initPresenter() {
        settingsPresenter.view = this
        settingsPresenter.onCreate()
    }

    fun logout() {
        settingsPresenter.logoutDialogPositiveClicked()
    }

    private fun initViews() {
        findPreference(getString(R.string.pref_logout)).setOnPreferenceClickListener {
            settingsPresenter.logoutItemClicked()
            true
        }
    }

    private fun initDialog() {
        val builder = AlertDialog.Builder(activity)

        val dialogClickListener = DialogInterface.OnClickListener { dialogInterface, which ->
            if (which == DialogInterface.BUTTON_POSITIVE) {
                settingsPresenter.logoutDialogPositiveClicked()
            }
        }

        logoutDialog = builder.setMessage(getString(R.string.login_exit_question))
                .setTitle(getString(R.string.login_exit_message))
                .setPositiveButton(getString(R.string.yes), dialogClickListener)
                .setNegativeButton(getString(R.string.no), dialogClickListener)
                .create()

        logoutProgressDialog.setMessage(resources.getString(R.string.pref_logout_progress_dialog_message))
    }

    override fun showLogoutConnectionError(message: String) {
        settingsActivityCallback?.showLogoutConnectionError(message)
    }

    override fun showLogoutDialog() {
        logoutDialog.show()
    }

    override fun hideLogoutDialog() {
        logoutDialog.dismiss()
    }

    override fun showLogoutProgressDialog() {
        logoutProgressDialog.show()
    }

    override fun hideLogoutProgressDialog() {
        logoutProgressDialog.dismiss()
    }

    override fun openLoginScreen() {
        val intent = Intent(activity, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        activity.finish()
    }

    override fun onResume() {
        super.onResume()
        settingsPresenter.onResume()
    }

    override fun onPause() {
        super.onPause()
        settingsPresenter.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        settingsPresenter.onDestroy()
    }

    override fun onAttach(context: Context) {
        settingsActivityCallback = activity as SettingsActivityCallback
        super.onAttach(context)
    }

    override fun onDetach() {
        settingsActivityCallback = null
        super.onDetach()
    }
}