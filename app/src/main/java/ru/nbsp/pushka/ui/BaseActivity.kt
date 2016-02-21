package ru.nbsp.pushka.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import ru.nbsp.pushka.BaseApplication
import ru.nbsp.pushka.annotation.AuthRequired
import ru.nbsp.pushka.auth.AccountManager
import ru.nbsp.pushka.ui.login.LoginActivity
import javax.inject.Inject

/**
 * Created by Dimorinny on 11.02.16.
 */
open class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var accountManager: AccountManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BaseApplication.graph.inject(this)

        if (isAuthRequired(javaClass)) {
            openLoginActivity()
            finish()
        }
    }

    internal fun isAuthRequired(clazz: Class<*>): Boolean {
        return if (clazz.isAnnotationPresent(AuthRequired::class.java)) {
            !accountManager.isAccountValid()
        } else {
            false
        }
    }

    internal fun openLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}