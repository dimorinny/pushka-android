package ru.nbsp.pushka.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import ru.nbsp.pushka.BaseApplication
import ru.nbsp.pushka.auth.AccountManager
import ru.nbsp.pushka.ui.login.LoginActivity
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

    open fun openLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}