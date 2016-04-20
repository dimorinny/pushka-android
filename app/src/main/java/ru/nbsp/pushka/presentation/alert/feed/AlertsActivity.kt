package ru.nbsp.pushka.presentation.alert.feed

import android.os.Bundle
import android.support.v4.app.Fragment
import ru.nbsp.pushka.BaseApplication
import ru.nbsp.pushka.R
import ru.nbsp.pushka.presentation.core.base.OneFragmentNavigationActivity

/**
 * Created by Dimorinny on 17.04.16.
 */
class AlertsActivity : OneFragmentNavigationActivity() {

    override fun getContentLayout(): Int {
        return R.layout.activity_one_fragment
    }

    override fun getDrawerItemId(): Int {
        return R.id.drawer_feed
    }

    override fun createFragment(): Fragment {
        return AlertsFragment()
    }

    override fun injectActivity() {
        BaseApplication.graph.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)

        // Init here
    }
}