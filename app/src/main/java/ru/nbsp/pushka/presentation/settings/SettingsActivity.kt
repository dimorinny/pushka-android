package ru.nbsp.pushka.presentation.settings

import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import ru.nbsp.pushka.R
import ru.nbsp.pushka.presentation.core.base.BaseActivity
import ru.nbsp.pushka.util.bindView

/**
 * Created by Dimorinny on 24.02.16.
 */
class SettingsActivity : BaseActivity(), SettingsActivityCallback {

    val toolbar: Toolbar by bindView(R.id.toolbar)
    val coordinatorContainer: CoordinatorLayout by bindView(R.id.coordinator_container)
    lateinit var fragment: SettingsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        initToolbar()
        initFragment()
    }

    private fun initFragment() {
        fragment = SettingsFragment()
        supportFragmentManager.beginTransaction().replace(R.id.main_container, fragment).commit()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            (supportActionBar as ActionBar).setDisplayHomeAsUpEnabled(true);
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun showLogoutConnectionError(message: String) {
        Snackbar.make(coordinatorContainer, message, Snackbar.LENGTH_SHORT).setAction(getString(R.string.snack_retry), {
            fragment.logout()
        }).show()
    }
}