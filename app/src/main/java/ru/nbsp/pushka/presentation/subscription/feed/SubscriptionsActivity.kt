package ru.nbsp.pushka.presentation.subscription.feed

import android.os.Build
import android.os.Bundle
import android.support.annotation.ColorRes
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.Menu
import com.miguelcatalan.materialsearchview.MaterialSearchView
import ru.nbsp.pushka.BaseApplication
import ru.nbsp.pushka.R
import ru.nbsp.pushka.presentation.core.base.OneFragmentNavigationActivity
import ru.nbsp.pushka.util.bindView

/**
 * Created by Dimorinny on 18.04.16.
 */
class SubscriptionsActivity : OneFragmentNavigationActivity() {

    val searchView: MaterialSearchView by bindView(R.id.search_view)

    override fun getDrawerItemId(): Int {
        return R.id.drawer_subscriptions
    }

    override fun createFragment(): Fragment {
        return SubscriptionsFragment()
    }

    override fun injectActivity() {
        BaseApplication.graph.inject(this)
    }

    override fun getContentLayout(): Int {
        return R.layout.activity_subscriptions
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViews()
    }

    private fun initViews() {
        searchView.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean { return false }

            override fun onQueryTextChange(newText: String): Boolean {
                (getFragment() as SubscriptionsFragment).onSearchQueryChanged(newText)
                return true
            }
        })

        searchView.setOnSearchViewListener(object : MaterialSearchView.SearchViewListener {
            override fun onSearchViewClosed() {
                setStatusBarColor(R.color.colorPrimaryDark)
            }

            override fun onSearchViewShown() {
                setStatusBarColor(R.color.light_grey)
            }
        })
    }

    private fun setStatusBarColor(@ColorRes color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, color)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search, menu)

        val item = menu.findItem(R.id.action_search)
        searchView.setMenuItem(item)

        return true
    }
}