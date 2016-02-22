package ru.nbsp.pushka.ui.navigation

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.TextView
import ru.nbsp.pushka.BaseApplication
import ru.nbsp.pushka.R
import ru.nbsp.pushka.auth.Account
import ru.nbsp.pushka.mvp.PresentedActivity
import ru.nbsp.pushka.mvp.presenters.navigation.NavigationPresenter
import ru.nbsp.pushka.mvp.presenters.navigation.drawer.DisableToogleAnimation
import ru.nbsp.pushka.ui.feed.FeedFragment
import ru.nbsp.pushka.util.bindView
import javax.inject.Inject

/**
 * Created by Dimorinny on 12.02.16.
 */
class NavigationActivity : PresentedActivity<NavigationPresenter>(),
        ru.nbsp.pushka.mvp.views.navigation.NavigationView,
        NavigationView.OnNavigationItemSelectedListener {

    val drawerLayout: DrawerLayout by bindView(R.id.drawer)
    val navigationView: NavigationView by bindView(R.id.navigation)
    val toolbar: Toolbar by bindView(R.id.toolbar)
    lateinit var headerName: TextView

    @Inject
    lateinit var presenter: NavigationPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isFinishing) {
            return
        }
        setContentView(R.layout.activity_navigation)
        BaseApplication.graph.inject(this)
        initPresenter(presenter)

        initToolbar()
        initViews()
        presenter.loadAccount()

        if (savedInstanceState == null) {
            setFeedContent()
        }
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        val toggle = DisableToogleAnimation(this, drawerLayout, toolbar)
        drawerLayout.setDrawerListener(toggle)
        toggle.syncState()
    }

    override fun initPresenter(presenter: NavigationPresenter) {
        presenter.view = this
        super.initPresenter(presenter)
    }

    private fun initViews() {
//        val builder = AlertDialog.Builder(this)
//
//        val dialogClickListener = DialogInterface.OnClickListener { dialogInterface, which ->
//            if (which == DialogInterface.BUTTON_POSITIVE) {
//                presenter.onExitDialogPositiveClicked()
//            }
//        }
//
//        exitDialog = builder.setMessage(getString(R.string.login_exit_question))
//                .setTitle(getString(R.string.login_exit_message))
//                .setPositiveButton(getString(R.string.yes), dialogClickListener)
//                .setNegativeButton(getString(R.string.no), dialogClickListener)
//                .create()
        headerName = navigationView.getHeaderView(0).findViewById(R.id.header_name) as TextView
        navigationView.setNavigationItemSelectedListener(this)
    }

    override fun setFeedContent() {
        setFragment(FeedFragment())
        navigationView.setCheckedItem(R.id.drawer_feed)
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        presenter.onDrawerItemClicked(item.itemId)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commitAllowingStateLoss()
    }

    override fun setAccount(account: Account) {
        headerName.text = account.firstName + " " + account.secondName
    }

    override fun openExitDialog() {

    }
}