package ru.nbsp.pushka.presentation.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import ru.nbsp.pushka.BaseApplication
import ru.nbsp.pushka.R
import ru.nbsp.pushka.util.IntentUtils
import ru.nbsp.pushka.util.bindView
import javax.inject.Inject

/**
 * Created by Dimorinny on 24.02.16.
 */
abstract class OneFragmentActivity : BaseActivity() {

    private lateinit var fragment: Fragment
    private lateinit var fragmentManager: FragmentManager

    val toolbar: Toolbar by bindView(R.id.toolbar)

    @Inject
    lateinit var intentUtils: IntentUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        BaseApplication.graph.inject(this)

        initToolbar()

        if (!isFinishing) {
            fragmentManager = supportFragmentManager

            var cachedFragment: Fragment? = fragmentManager.findFragmentById(R.id.main_container)

            if (cachedFragment == null) {
                cachedFragment = createFragment()
                cachedFragment.arguments = intentUtils.intentToFragmentArguments(cachedFragment.arguments, intent)

                fragmentManager.beginTransaction().replace(R.id.main_container, cachedFragment).commit()
            }

            fragment = cachedFragment
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

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            (supportActionBar as ActionBar).setDisplayHomeAsUpEnabled(true);
        }
    }

    protected open fun getLayout(): Int {
        return R.layout.activity_one_fragment
    }

    protected abstract fun createFragment(): Fragment

    protected open fun getFragment(): Fragment {
        return fragment
    }
}