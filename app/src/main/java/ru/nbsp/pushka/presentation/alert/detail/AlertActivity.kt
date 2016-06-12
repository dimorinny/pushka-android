package ru.nbsp.pushka.presentation.alert.detail

import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.ActionBar
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.gordonwong.materialsheetfab.DimOverlayFrameLayout
import com.gordonwong.materialsheetfab.MaterialSheetFab
import com.gordonwong.materialsheetfab.MaterialSheetFabEventListener
import ru.nbsp.pushka.BaseApplication
import ru.nbsp.pushka.BuildConfig
import ru.nbsp.pushka.R
import ru.nbsp.pushka.presentation.PresentedActivity
import ru.nbsp.pushka.presentation.alert.detail.adapter.AlertActionsAdapter
import ru.nbsp.pushka.presentation.core.adapter.OnItemClickListener
import ru.nbsp.pushka.presentation.core.model.alert.PresentationAction
import ru.nbsp.pushka.presentation.core.widget.AnimatedFloatingActionButton
import ru.nbsp.pushka.util.bindView
import javax.inject.Inject

/**
 * Created by Dimorinny on 16.03.16.
 */
class AlertActivity : PresentedActivity<AlertPresenter>(), AlertView {

    companion object {
        final val ARG_ALERT_ID = "arg_alert_id"
    }

    val contentWebView: WebView by bindView(R.id.alert_web_view)
    val toolbar: Toolbar by bindView(R.id.toolbar)
    val floatingActionButton: AnimatedFloatingActionButton by bindView(R.id.alert_fab)
    val overlay: DimOverlayFrameLayout by bindView(R.id.alert_overlay)
    val sheetLayout: CardView by bindView(R.id.alert_fab_sheet)
    val recyclerView: RecyclerView by bindView(R.id.alert_action_recycler_view)
    val adContainer: ViewGroup by bindView(R.id.ad_container)
    var ad: AdView? = null

    @Inject
    lateinit var presenter: AlertPresenter
    lateinit var alertActionsAdapter: AlertActionsAdapter
    lateinit var sheetFab: MaterialSheetFab<AnimatedFloatingActionButton>
    lateinit var alertId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alert)
        BaseApplication.graph.inject(this)

        initPresenter(presenter)
        initArgs()
        initState(savedInstanceState)
        initToolbar()
        initViews()
        initAdView()
        initAd()

        presenter.loadAlertFromCache(alertId)
        presenter.loadAlertFromServer(alertId)
    }

    private fun initState(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            contentWebView.restoreState(savedInstanceState)
        }
    }

    private fun initArgs() {
        if (intent.extras.getString(ARG_ALERT_ID) != null) {
            alertId = intent.extras.getString(ARG_ALERT_ID)
        }
    }

    // I create ad view dynamically because stupid admob's programmers are monkeys
    private fun initAdView() {
        ad = AdView(applicationContext)
        ad?.adSize = AdSize.SMART_BANNER
        ad?.adUnitId = resources.getString(R.string.admob_unit_id)
        adContainer.addView(ad)
    }

    private fun initViews() {
        contentWebView.setWebViewClient(WebViewClient())
        contentWebView.settings.javaScriptEnabled = true

        sheetFab = MaterialSheetFab(floatingActionButton, sheetLayout, overlay, ContextCompat.getColor(this, R.color.white), ContextCompat.getColor(this, R.color.white))
        sheetFab.setEventListener(object : MaterialSheetFabEventListener() {

            var statusBarColor: Int? = null

            override fun onShowSheet() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    statusBarColor = window.statusBarColor
                    window.statusBarColor = ContextCompat.getColor(this@AlertActivity, R.color.colorPrimaryDark2)
                }
            }

            override fun onHideSheet() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.statusBarColor = statusBarColor!!
                }
            }
        })

        initActionsRecyclerView()
    }

    private fun initAd() {
        val adBuilder = AdRequest.Builder()

        if (BuildConfig.DEBUG) {
            adBuilder.addTestDevice("134C8B7A80A1936D85748EF141D344C4")
        }

        val adRequest = adBuilder.build()
        ad?.loadAd(adRequest)
    }

    private fun initActionsRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)

        alertActionsAdapter = AlertActionsAdapter()
        recyclerView.adapter = alertActionsAdapter

        alertActionsAdapter.itemClickListener = object : OnItemClickListener {
            override fun onItemClicked(index: Int, view: View) {
                presenter.onActionClicked(index)
            }
        }
    }

    override fun initPresenter(presenter: AlertPresenter) {
        presenter.view = this
        super.initPresenter(presenter)
    }

    override fun setContentUrl(url: String) {
        if (contentWebView.url != url) {
            contentWebView.loadUrl(url)
        }
    }

    override fun setTitle(alertTitle: String) {
        title = alertTitle
    }

    override fun setActions(actions: List<PresentationAction>) {
        alertActionsAdapter.actions = actions
        sheetFab.showFab()
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
        if (sheetFab.isSheetVisible) {
            sheetFab.hideSheet();
        } else {
            super.onBackPressed();
        }
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            (supportActionBar as ActionBar).setDisplayHomeAsUpEnabled(true);
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        contentWebView.saveState(outState)
        super.onSaveInstanceState(outState)
    }

    override fun onPause() {
        ad?.pause()
        super.onPause()
    }

    override fun onStop() {
        ad?.destroy()
        super.onStop()
    }

    override fun onResume() {
        ad?.resume()
        super.onResume()
    }
}