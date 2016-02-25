package ru.nbsp.pushka.presentation.core.base

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import ru.nbsp.pushka.BaseApplication
import ru.nbsp.pushka.R
import ru.nbsp.pushka.auth.AccountManager
import ru.nbsp.pushka.presentation.login.LoginActivity
import javax.inject.Inject

/**
 * Created by Dimorinny on 11.02.16.
 */
open class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var accountManager: AccountManager
    val exceptionActivities: Array<Class<*>> = arrayOf(LoginActivity::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BaseApplication.graph.inject(this)

        if (javaClass !in exceptionActivities && accountManager.getAccount() == null) {
            Log.v("qwe", "qwe")
            openLoginActivity()
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        initToolbar()
    }

    private fun initToolbar() {
        val toolbar = findViewById(R.id.toolbar) as Toolbar?
        if (toolbar != null) {
            setSupportActionBar(toolbar)
        }
    }

    open fun openLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}