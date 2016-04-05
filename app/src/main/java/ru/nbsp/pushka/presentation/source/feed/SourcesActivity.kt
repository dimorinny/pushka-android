package ru.nbsp.pushka.presentation.source.feed

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.ImageView
import ru.nbsp.pushka.BaseApplication
import ru.nbsp.pushka.R
import ru.nbsp.pushka.presentation.core.base.BaseActivity
import ru.nbsp.pushka.presentation.core.model.source.PresentationCategory
import ru.nbsp.pushka.util.ColorUtils
import ru.nbsp.pushka.util.IconUtils
import ru.nbsp.pushka.util.IntentUtils
import ru.nbsp.pushka.util.bindView
import javax.inject.Inject

/**
 * Created by Dimorinny on 08.03.16.
 */
class SourcesActivity : BaseActivity() {

    companion object {
        const val ARG_CATEGORY = "arg_category"
        const val STATE_CATEGORY = "state_category"
    }

    lateinit var fragment: Fragment
    lateinit var category: PresentationCategory

    val toolbar: Toolbar by bindView(R.id.toolbar)
    val toolbarImage: ImageView by bindView(R.id.toolbar_icon)
    val collapsingToolbar: CollapsingToolbarLayout by bindView(R.id.collapsing_toolbar)
    val toolbarContainer: AppBarLayout by bindView(R.id.toolbar_container)

    @Inject
    lateinit var intentUtils: IntentUtils

    @Inject
    lateinit var colorUtils: ColorUtils

    @Inject
    lateinit var iconUtils: IconUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sources)
        BaseApplication.graph.inject(this)

        initToolbar()
        initArguments()
        initState(savedInstanceState)
        initViews()
        initFragment()
        setViewData()
    }

    private fun initViews() {
        initColors()
        toolbarImage.setImageResource(iconUtils.getIcon(category.icon))
    }

    private fun initColors() {
        toolbarContainer.setBackgroundColor(Color.parseColor(category.color))
        collapsingToolbar.setContentScrimColor(Color.parseColor(category.color))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = colorUtils.darker(Color.parseColor(category.color))
        }
    }

    private fun setViewData() {
        collapsingToolbar.title = category.name
    }

    private fun initFragment() {
        val fragmentManager = supportFragmentManager

        var cachedFragment: Fragment? = fragmentManager.findFragmentById(R.id.container)

        if (cachedFragment == null) {
            cachedFragment = SourcesFragment()
            cachedFragment.arguments = intentUtils.intentToFragmentArguments(cachedFragment.arguments, intent)

            fragmentManager.beginTransaction().replace(R.id.container, cachedFragment).commitAllowingStateLoss()
        }

        fragment = cachedFragment
    }

    private fun initArguments() {
        if (intent.getSerializableExtra(ARG_CATEGORY) != null) {
            category = intent.extras.getSerializable(ARG_CATEGORY) as PresentationCategory
        }
    }

    private fun initState(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            if (savedInstanceState.getSerializable(STATE_CATEGORY) != null) {
                category = savedInstanceState.getSerializable(STATE_CATEGORY) as PresentationCategory
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        finish()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            (supportActionBar as ActionBar).setDisplayHomeAsUpEnabled(true);
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable(STATE_CATEGORY, category)
        super.onSaveInstanceState(outState)
    }
}