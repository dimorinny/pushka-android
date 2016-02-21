package ru.nbsp.pushka.ui.navigation

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.holder.BadgeStyle
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import ru.nbsp.pushka.BaseApplication
import ru.nbsp.pushka.R
import ru.nbsp.pushka.mvp.PresentedActivity
import ru.nbsp.pushka.mvp.presenters.navigation.NavigationPresenter
import ru.nbsp.pushka.mvp.views.navigation.NavigationView
import ru.nbsp.pushka.ui.feed.FeedFragment
import ru.nbsp.pushka.ui.navigation.drawer.NavigationDrawerItem
import ru.nbsp.pushka.util.bindView
import java.util.*
import javax.inject.Inject

/**
 * Created by Dimorinny on 12.02.16.
 */
class NavigationActivity : PresentedActivity<NavigationPresenter>(), NavigationView {

    private lateinit var drawer: Drawer
    private lateinit var accountHeader: AccountHeader
    private val profileDrawerItem = ProfileDrawerItem()

    val toolbar: Toolbar by bindView(R.id.toolbar)

    @Inject
    lateinit var presenter: NavigationPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
        BaseApplication.graph.inject(this)

        initPresenter(presenter)
        initToolbar()
        initAccountHeader()
        initDrawer()

        if (savedInstanceState == null) {
            setFeedContent()
        }
    }

    override fun initPresenter(presenter: NavigationPresenter) {
        presenter.view = this
        super.initPresenter(presenter)
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
    }

    private fun initAccountHeader() {
        accountHeader = AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.color.primary_dark)
                .addProfiles(profileDrawerItem)
                .withAlternativeProfileHeaderSwitching(false)
                .withSelectionListEnabled(false).build()
    }

    private fun initDrawer() {
        drawer = DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withAccountHeader(accountHeader)
                .withDrawerItems(getDrawerItems())
                .withOnDrawerItemClickListener { view, index, item ->
                    presenter.onDrawerItemClicked(NavigationDrawerItem.values()[item.identifier])
                }
                .withCloseOnClick(true)
                .withHeaderDivider(false)
                .build()
    }

    private fun getDrawerItems(): ArrayList<IDrawerItem<*>> {
        val items = ArrayList<IDrawerItem<*>>()

        for (item in NavigationDrawerItem.values()) {
            if (item.isDivider) {
                items.add(DividerDrawerItem())
            } else {
                items.add(PrimaryDrawerItem()
                        .withName(getString(item.title))
                        .withIcon(item.icon)
                        .withIdentifier(item.ordinal)
                        .withIconTintingEnabled(true)
                        .withSelectable(false)
                        .withBadgeStyle(BadgeStyle()
                                .withTextColor(Color.WHITE)
                                .withColorRes(R.color.md_red_700)))
            }
        }

        return items
    }

    override fun setFeedContent() {
        setFragment(FeedFragment())
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commitAllowingStateLoss()
    }
}