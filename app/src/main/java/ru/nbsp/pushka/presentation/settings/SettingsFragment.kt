package ru.nbsp.pushka.presentation.settings

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceFragment
import android.support.v7.app.AlertDialog
import ru.nbsp.pushka.BaseApplication
import ru.nbsp.pushka.R
import ru.nbsp.pushka.auth.AccountManager
import ru.nbsp.pushka.presentation.login.LoginActivity
import javax.inject.Inject

/**
 * Created by Dimorinny on 24.02.16.
 */
class SettingsFragment : PreferenceFragment() {

    private lateinit var logoutDialog: AlertDialog

    @Inject
    lateinit var accountManager: AccountManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.preferences)
        BaseApplication.graph.inject(this)

        initDialog()
        initViews()
    }

    private fun initViews() {
        findPreference(getString(R.string.pref_logout)).setOnPreferenceClickListener {
            showLogoutDialog()
        }
    }

    private fun initDialog() {
        val builder = AlertDialog.Builder(activity)

        val dialogClickListener = DialogInterface.OnClickListener { dialogInterface, which ->
            if (which == DialogInterface.BUTTON_POSITIVE) {
                onLogoutDialogPositiveClicked()
            }
        }

        logoutDialog = builder.setMessage(getString(R.string.login_exit_question))
                .setTitle(getString(R.string.login_exit_message))
                .setPositiveButton(getString(R.string.yes), dialogClickListener)
                .setNegativeButton(getString(R.string.no), dialogClickListener)
                .create()
    }

    private fun showLogoutDialog(): Boolean {
        logoutDialog.show()
        return true
    }

    private fun onLogoutDialogPositiveClicked() {
        logoutDialog.dismiss()
        accountManager.clear()
        openLoginActivity()
    }

    private fun openLoginActivity() {
        val intent = Intent(activity, LoginActivity::class.java)
        startActivity(intent)
        activity.finish()
    }
}