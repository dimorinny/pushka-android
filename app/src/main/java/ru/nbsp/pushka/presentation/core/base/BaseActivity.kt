package ru.nbsp.pushka.presentation.core.base

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import ru.nbsp.pushka.BaseApplication
import ru.nbsp.pushka.network.auth.AccountManager
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
            openLoginActivity()
        }
    }

//    private fun initToolbar() {
//        val toolbar = findViewById(R.id.toolbar) as Toolbar?
//        if (toolbar != null) {
//            setSupportActionBar(toolbar)
//        }
//    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
//        initToolbar()
    }

    open fun openLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}