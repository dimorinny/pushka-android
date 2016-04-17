package ru.nbsp.pushka.presentation.subscription.feed

import android.os.Bundle
import android.support.v4.app.Fragment
import ru.nbsp.pushka.BaseApplication
import ru.nbsp.pushka.R
import ru.nbsp.pushka.presentation.core.base.OneFragmentNavigationActivity

/**
 * Created by Dimorinny on 18.04.16.
 */
class SubscriptionsActivity : OneFragmentNavigationActivity() {

    override fun getContentLayout(): Int {
        return R.layout.activity_one_fragment
    }

    override fun getDrawerItemId(): Int {
        return R.id.drawer_subscriptions
    }

    override fun createFragment(): Fragment {
        return SubscriptionsFragment()
    }

    override fun injectActivity() {
        BaseApplication.graph.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Init here
    }
}