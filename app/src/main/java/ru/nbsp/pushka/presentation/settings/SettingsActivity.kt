package ru.nbsp.pushka.presentation.settings

import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import ru.nbsp.pushka.R
import ru.nbsp.pushka.presentation.core.base.BaseActivity
import ru.nbsp.pushka.util.bindView

/**
 * Created by Dimorinny on 24.02.16.
 */
class SettingsActivity: BaseActivity() {

    val toolbar: Toolbar by bindView(R.id.toolbar)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_fragment)
        initToolbar()

        fragmentManager.beginTransaction().replace(R.id.main_container, SettingsFragment()).commit()
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
}