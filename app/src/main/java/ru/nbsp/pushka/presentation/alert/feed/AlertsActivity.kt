package ru.nbsp.pushka.presentation.alert.feed

import android.os.Build
import android.os.Bundle
import android.support.annotation.ColorRes
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.Menu
import com.miguelcatalan.materialsearchview.MaterialSearchView
import ru.nbsp.pushka.BaseApplication
import ru.nbsp.pushka.R
import ru.nbsp.pushka.presentation.core.base.OneFragmentNavigationActivity
import ru.nbsp.pushka.util.bindView

/**
 * Created by Dimorinny on 17.04.16.
 */
class AlertsActivity : OneFragmentNavigationActivity(), AlertsActivityCallback {

    val searchView: MaterialSearchView by bindView(R.id.search_view)

    override fun getDrawerItemId(): Int {
        return R.id.drawer_feed
    }

    override fun createFragment(): Fragment {
        return AlertsFragment()
    }

    override fun injectActivity() {
        BaseApplication.graph.inject(this)
    }


    override fun getContentLayout(): Int {
        return R.layout.activity_alerts
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!isFinishing) {
            initViews()
        }
    }

    private fun initViews() {
        searchView.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean { return false }

            override fun onQueryTextChange(newText: String): Boolean {
                (getFragment() as AlertsFragment).onSearchQueryChanged(newText)
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

    override fun showMessage(message: String) {
        Snackbar.make(container, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun showLoadAlertsConnectionError(message: String) {
        Snackbar.make(container, message, Snackbar.LENGTH_SHORT).setAction(getString(R.string.snack_retry), {
            (getFragment() as AlertsFragment).loadAlertsFromServer()
        }).show()
    }
}