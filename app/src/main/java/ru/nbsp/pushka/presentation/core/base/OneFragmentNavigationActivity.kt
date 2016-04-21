package ru.nbsp.pushka.presentation.core.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import ru.nbsp.pushka.R
import ru.nbsp.pushka.presentation.navigation.NavigationActivity
import ru.nbsp.pushka.util.IntentUtils
import javax.inject.Inject

/**
 * Created by Dimorinny on 17.04.16.
 */
abstract class OneFragmentNavigationActivity : NavigationActivity() {

    private lateinit var fragment: Fragment
    private lateinit var fragmentManager: FragmentManager

    @Inject
    lateinit var intentUtils: IntentUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

    override fun hasConnectionIndicator(): Boolean {
        return true
    }

    override fun getContentLayout(): Int {
        return R.layout.activity_navigation_one_fragment
    }

    protected abstract fun createFragment(): Fragment

    protected open fun getFragment(): Fragment {
        return fragment
    }
}