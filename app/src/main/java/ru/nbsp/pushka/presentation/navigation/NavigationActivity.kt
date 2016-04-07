package ru.nbsp.pushka.presentation.navigation

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.annotation.StringRes
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import com.gordonwong.materialsheetfab.DimOverlayFrameLayout
import com.gordonwong.materialsheetfab.MaterialSheetFab
import com.gordonwong.materialsheetfab.MaterialSheetFabEventListener
import com.squareup.picasso.Picasso
import ru.nbsp.pushka.BaseApplication
import ru.nbsp.pushka.R
import ru.nbsp.pushka.network.auth.Account
import ru.nbsp.pushka.presentation.PresentedActivity
import ru.nbsp.pushka.presentation.alert.feed.AlertsFragment
import ru.nbsp.pushka.presentation.category.feed.CategoriesFragment
import ru.nbsp.pushka.presentation.core.model.device.PresentationDeviceType
import ru.nbsp.pushka.presentation.core.state.State
import ru.nbsp.pushka.presentation.core.state.StateManager
import ru.nbsp.pushka.presentation.core.widget.AnimatedFloatingActionButton
import ru.nbsp.pushka.presentation.device.feed.DevicesFragment
import ru.nbsp.pushka.presentation.navigation.adapter.DeviceTypesAdapter
import ru.nbsp.pushka.presentation.navigation.drawer.DisableToogleAnimation
import ru.nbsp.pushka.presentation.settings.SettingsActivity
import ru.nbsp.pushka.presentation.source.feed.SourcesFragment
import ru.nbsp.pushka.presentation.subscription.feed.SubscriptionsFragment
import ru.nbsp.pushka.util.bindView
import javax.inject.Inject

/**
 * Created by Dimorinny on 12.02.16.
 */
class NavigationActivity : PresentedActivity<NavigationPresenter>(),
        ru.nbsp.pushka.presentation.navigation.NavigationView,
        NavigationView.OnNavigationItemSelectedListener,
        StateManager {

    companion object {
        private const val NAVDRAWER_LAUNCH_DELAY = 258L
        private const val STATE_TITLE = "state_title"
        private const val STATE_CURRENT_DRAWER_ITEM_ID = "state_current_drawer_item_id"
    }

    val handler: Handler = Handler()

    lateinit var currentFragmentTitle: String
    var currentDrawerItemId: Int = R.id.drawer_feed

    val drawerLayout: DrawerLayout by bindView(R.id.drawer)
    val navigationView: NavigationView by bindView(R.id.navigation)
    val toolbar: Toolbar by bindView(R.id.toolbar)
    val devicesFab: AnimatedFloatingActionButton by bindView(R.id.devices_fab)
    val overlay: DimOverlayFrameLayout by bindView(R.id.overlay)
    val sheetLayout: CardView by bindView(R.id.devices_fab_sheet)
    val devicesRecyclerView: RecyclerView by bindView(R.id.devices_recycler_view)

    lateinit var headerName: TextView
    lateinit var headerEmail: TextView
    lateinit var headerPhoto: ImageView
    lateinit var devicesSheetFab: MaterialSheetFab<AnimatedFloatingActionButton>
    lateinit var devicesAdapter: DeviceTypesAdapter

    @Inject
    lateinit var presenter: NavigationPresenter

    @Inject
    lateinit var picasso: Picasso

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isFinishing) {
            return
        }
        setContentView(R.layout.activity_navigation)
        BaseApplication.graph.inject(this)

        initState(savedInstanceState)
        initStatusBar()
        initToolbar()
        initViews()

        initPresenter(presenter)
        presenter.loadAccount()

        if (savedInstanceState == null) {
            setFeedContent()
        } else {
            title = currentFragmentTitle

            if (currentDrawerItemId == R.id.drawer_devices) {
                devicesFab.show()
            }
        }
    }

    private fun initState(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(STATE_TITLE)) {
                currentFragmentTitle = savedInstanceState.getString(STATE_TITLE)
            }
            if (savedInstanceState.containsKey(STATE_CURRENT_DRAWER_ITEM_ID)) {
                currentDrawerItemId = savedInstanceState.getInt(STATE_CURRENT_DRAWER_ITEM_ID)
            }
        }
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

    override fun initPresenter(presenter: NavigationPresenter) {
        presenter.view = this
        super.initPresenter(presenter)
    }

    private fun initViews() {
        val headerView = navigationView.getHeaderView(0)
        headerName = headerView.findViewById(R.id.header_name) as TextView
        headerEmail = headerView.findViewById(R.id.header_email) as TextView
        headerPhoto = headerView.findViewById(R.id.header_photo) as ImageView

        navigationView.setNavigationItemSelectedListener(this)

        devicesSheetFab = MaterialSheetFab(devicesFab, sheetLayout, overlay,
                ContextCompat.getColor(this, R.color.white), ContextCompat.getColor(this, R.color.white))
        devicesSheetFab.setEventListener(object : MaterialSheetFabEventListener() {

            var statusBarColor: Int? = null

            override fun onShowSheet() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    statusBarColor = window.statusBarColor
                    window.statusBarColor = ContextCompat.getColor(this@NavigationActivity, R.color.colorPrimaryDark2)
                }
            }

            override fun onHideSheet() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.statusBarColor = statusBarColor!!
                }
            }
        })

        initDevicesFabRecyclerView()
    }

    private fun initDevicesFabRecyclerView() {
        devicesRecyclerView.layoutManager = LinearLayoutManager(this)

        devicesAdapter = DeviceTypesAdapter(this)
        devicesAdapter.deviceTypeClickListener = object : DeviceTypesAdapter.OnDeviceTypeItemClickListener {
            override fun onItemClicked(deviceType: PresentationDeviceType) {
                presenter.onCreateDeviceItemClicked(deviceType)
            }
        }
        devicesRecyclerView.adapter = devicesAdapter
    }

    override fun setDeviceTypes(deviceTypes: List<PresentationDeviceType>) {
        devicesAdapter.deviceTypes = deviceTypes
    }

    override fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

    override fun setFeedContent() {
        titleFromStringRes(R.string.title_feed)
        setFragment(AlertsFragment())
        navigationView.setCheckedItem(R.id.drawer_feed)
    }

    override fun setSourcesContent() {
        titleFromStringRes(R.string.title_sources)
        setFragment(SourcesFragment())
        navigationView.setCheckedItem(R.id.drawer_sources)
    }

    override fun setCategoriesContent() {
        titleFromStringRes(R.string.title_sources)
        setFragment(CategoriesFragment())
        navigationView.setCheckedItem(R.id.drawer_sources)
    }

    override fun setSubscriptionsContent() {
        titleFromStringRes(R.string.title_subscription)
        setFragment(SubscriptionsFragment())
        navigationView.setCheckedItem(R.id.drawer_subscriptions)
    }

    override fun setDevicesContent() {
        titleFromStringRes(R.string.title_devices)
        setFragment(DevicesFragment())
        navigationView.setCheckedItem(R.id.drawer_devices)
    }

    override fun setSettingsContent() {
        handler.postDelayed({
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }, NAVDRAWER_LAUNCH_DELAY)
    }

    override fun setState(state: State) {
        title = when (state) {
            State.STATE_NORMAL, State.STATE_EMPTY -> currentFragmentTitle
            State.STATE_PROGRESS -> resources.getString(R.string.title_loading)
            State.STATE_ERROR -> resources.getString(R.string.title_error)
        }
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else if (devicesSheetFab.isSheetVisible) {
            devicesSheetFab.hideSheet();
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawerLayout.closeDrawer(GravityCompat.START)

        if (currentDrawerItemId == item.itemId) {
            return false
        }

        devicesFab.hide()
        currentDrawerItemId = item.itemId
        presenter.onDrawerItemClicked(item.itemId)

        if (currentDrawerItemId == R.id.drawer_devices) {
            devicesFab.show()
        }

        return true
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commitAllowingStateLoss()
    }

    private fun titleFromStringRes(@StringRes res: Int) {
        title = resources.getString(res)
        currentFragmentTitle = title.toString()
    }

    override fun setAccount(account: Account) {
        headerName.text = "${account.firstName} ${account.secondName}"
        picasso.load(account.photo).into(headerPhoto)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        if (title != null) {
            outState.putString(STATE_TITLE, currentFragmentTitle)
        }
        outState.putInt(STATE_CURRENT_DRAWER_ITEM_ID, currentDrawerItemId)
        super.onSaveInstanceState(outState)
    }
}