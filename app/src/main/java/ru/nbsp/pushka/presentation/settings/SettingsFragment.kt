package ru.nbsp.pushka.presentation.settings

import android.content.DialogInterface
import android.os.Bundle
import android.preference.PreferenceFragment
import android.support.v7.app.AlertDialog
import ru.nbsp.pushka.BaseApplication
import ru.nbsp.pushka.R
import ru.nbsp.pushka.gcm.manage.GcmManager
import ru.nbsp.pushka.interactor.app.ApplicationInteractor
import ru.nbsp.pushka.network.auth.AccountManager
import javax.inject.Inject

/**
 * Created by Dimorinny on 24.02.16.
 */
class SettingsFragment : PreferenceFragment() {

    lateinit var logoutDialog: AlertDialog

    @Inject
    lateinit var accountManager: AccountManager

    @Inject
    lateinit var gcmManager: GcmManager

    @Inject
    lateinit var applicationInteractor: ApplicationInteractor

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
        gcmManager.clear()
        applicationInteractor.logout()
        activity.finish()
    }
}