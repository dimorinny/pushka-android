package ru.nbsp.pushka.presentation.navigation

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.github.pwittchen.reactivenetwork.library.ConnectivityStatus
import com.github.pwittchen.reactivenetwork.library.ReactiveNetwork
import com.squareup.picasso.Picasso
import ru.nbsp.pushka.R
import ru.nbsp.pushka.network.auth.Account
import ru.nbsp.pushka.presentation.PresentedActivity
import ru.nbsp.pushka.presentation.alert.feed.AlertsActivity
import ru.nbsp.pushka.presentation.category.feed.CategoriesActivity
import ru.nbsp.pushka.presentation.device.feed.container.DevicesActivity
import ru.nbsp.pushka.presentation.navigation.drawer.DisableToogleAnimation
import ru.nbsp.pushka.presentation.settings.SettingsActivity
import ru.nbsp.pushka.presentation.subscription.feed.SubscriptionsActivity
import ru.nbsp.pushka.util.bindView
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

/**
 * Created by Dimorinny on 12.02.16.
 */
abstract class NavigationActivity : PresentedActivity<NavigationPresenter>(),
        ru.nbsp.pushka.presentation.navigation.NavigationView,
        NavigationView.OnNavigationItemSelectedListener {

    companion object {
        private const val NAVDRAWER_LAUNCH_DELAY = 240L
    }

    val handler: Handler = Handler()

    val container: ViewGroup by bindView(R.id.container)
    val drawerLayout: DrawerLayout by bindView(R.id.drawer)
    val navigationView: NavigationView by bindView(R.id.navigation)
    val toolbar: Toolbar by bindView(R.id.toolbar)
    val connectionContainer: ViewGroup by bindView(R.id.item_connection_container)

    lateinit var headerName: TextView
    lateinit var headerEmail: TextView
    lateinit var headerPhoto: ImageView

    val subscription = CompositeSubscription()

    @Inject
    lateinit var presenter: NavigationPresenter

    @Inject
    lateinit var picasso: Picasso

    abstract fun getContentLayout(): Int
    abstract fun getDrawerItemId(): Int
    abstract fun injectActivity()
    abstract fun hasConnectionIndicator(): Boolean

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!isFinishing) {
            setContentView(R.layout.activity_navigation)
            initContentLayout()
            injectActivity()

            initStatusBar()
            initToolbar()
            initViews()

            initConnectionListener()

            initPresenter(presenter)
            presenter.loadAccount()
        }
    }

    private fun initContentLayout() {
        val content = getContentLayout()
        LayoutInflater.from(this).inflate(content, container)
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        val toggle = DisableToogleAnimation(this, drawerLayout, toolbar)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun initStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = Color.TRANSPARENT;
        }
    }

    private fun initViews() {
        val headerView = navigationView.getHeaderView(0)
        headerName = headerView.findViewById(R.id.header_name) as TextView
        headerEmail = headerView.findViewById(R.id.header_email) as TextView
        headerPhoto = headerView.findViewById(R.id.header_photo) as ImageView

        navigationView.setNavigationItemSelectedListener(this)
        navigationView.setCheckedItem(getDrawerItemId())
    }

    private fun initConnectionListener() {
        if (hasConnectionIndicator()) {
            subscription.add(ReactiveNetwork()
                    .enableInternetCheck()
                    .observeConnectivity(this)
                    .subscribe {
                        connectionContainer.visibility = if (it == ConnectivityStatus.OFFLINE) View.VISIBLE else View.GONE
                    })
        }
    }

    override fun setFeedContent() {
        startActivityDelayed(AlertsActivity::class.java)
    }

    override fun setCategoriesContent() {
        startActivityDelayed(CategoriesActivity::class.java)
    }

    override fun setSubscriptionsContent() {
        startActivityDelayed(SubscriptionsActivity::class.java)
    }

    override fun setDevicesContent() {
        startActivityDelayed(DevicesActivity::class.java)
    }

    override fun setSettingsContent() {
        startActivityDelayed(SettingsActivity::class.java, false)
    }

    private fun <T> startActivityDelayed(clazz: Class<T>, newTask: Boolean = true) {
        handler.postDelayed({
            val intent = Intent(this, clazz)
            startActivity(intent)
            if (newTask) {
                overridePendingTransition(0, 0)
                finish()
            }
        }, NAVDRAWER_LAUNCH_DELAY)
    }


    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawerLayout.closeDrawer(GravityCompat.START)
        presenter.onDrawerItemClicked(item.itemId)
        return true
    }

    override fun setAccount(account: Account) {
        headerName.text = "${account.firstName} ${account.secondName}"
        picasso.load(account.photo).into(headerPhoto)
    }

    override fun initPresenter(presenter: NavigationPresenter) {
        presenter.view = this
        super.initPresenter(presenter)
    }

    override fun onDestroy() {
        subscription.unsubscribe()
        super.onDestroy()
    }
}