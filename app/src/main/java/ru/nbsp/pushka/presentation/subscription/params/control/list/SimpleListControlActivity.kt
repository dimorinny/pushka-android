package ru.nbsp.pushka.presentation.subscription.params.control

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.app.Fragment
import ru.nbsp.pushka.presentation.core.base.BaseActivity
import ru.nbsp.pushka.presentation.core.base.OneFragmentActivity

/**
 * Created by egor on 20.03.16.
 */
class SimpleListControlActivity: OneFragmentActivity() {



    override fun createFragment(): Fragment {
        return SimpleListFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

    }
}