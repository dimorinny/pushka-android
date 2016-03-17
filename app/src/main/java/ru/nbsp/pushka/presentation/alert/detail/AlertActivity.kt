package ru.nbsp.pushka.presentation.alert.detail

import android.os.Build
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.gordonwong.materialsheetfab.DimOverlayFrameLayout
import com.gordonwong.materialsheetfab.MaterialSheetFab
import com.gordonwong.materialsheetfab.MaterialSheetFabEventListener
import ru.nbsp.pushka.BaseApplication
import ru.nbsp.pushka.R
import ru.nbsp.pushka.presentation.PresentedActivity
import ru.nbsp.pushka.presentation.alert.detail.adapter.AlertActionsAdapter
import ru.nbsp.pushka.presentation.core.adapter.OnItemClickListener
import ru.nbsp.pushka.presentation.core.model.alert.PresentationAlert
import ru.nbsp.pushka.presentation.core.widget.AnimatedFloatingActionButton
import ru.nbsp.pushka.util.bindView
import javax.inject.Inject

/**
 * Created by Dimorinny on 16.03.16.
 */
class AlertActivity : PresentedActivity<AlertPresenter>(), AlertView {

    companion object {
        final val ARG_ALERT = "arg_alert"
    }

    val contentWebView: WebView by bindView(R.id.alert_web_view)
    val toolbar: Toolbar by bindView(R.id.toolbar)
    val floatingActionButton: AnimatedFloatingActionButton by bindView(R.id.alert_fab)
    val overlay: DimOverlayFrameLayout by bindView(R.id.alert_overlay)
    val sheetLayout: CardView by bindView(R.id.alert_fab_sheet)
    val recyclerView: RecyclerView by bindView(R.id.alert_action_recycler_view)

    @Inject
    lateinit var presenter: AlertPresenter
    lateinit var alert: PresentationAlert
    lateinit var alertActionsAdapter: AlertActionsAdapter
    lateinit var sheetFab: MaterialSheetFab<AnimatedFloatingActionButton>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alert)
        BaseApplication.graph.inject(this)

        initPresenter(presenter)
        initArgs()
        initToolbar()
        initViews()
        setContentUrl(alert.actions[0].value)
    }

    private fun initArgs() {
        if (intent.extras.getSerializable(ARG_ALERT) != null) {
            alert = intent.extras.getSerializable(ARG_ALERT) as PresentationAlert
        }
    }

    private fun initViews() {
        contentWebView.setWebViewClient(WebViewClient())
        contentWebView.settings.javaScriptEnabled = true

        sheetFab = MaterialSheetFab(floatingActionButton, sheetLayout, overlay, resources.getColor(R.color.white), resources.getColor(R.color.white))
        sheetFab.setEventListener(object : MaterialSheetFabEventListener() {

            var statusBarColor: Int? = null

            override fun onShowSheet() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    statusBarColor = window.statusBarColor
                    window.statusBarColor = resources.getColor(R.color.colorPrimaryDark2)
                }
            }

            override fun onHideSheet() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.statusBarColor = statusBarColor!!
                }
            }
        })

        initRecyclerView()
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)

        alertActionsAdapter = AlertActionsAdapter()
        recyclerView.adapter = alertActionsAdapter

        alertActionsAdapter.itemClickListener = object : OnItemClickListener {
            override fun onItemClicked(index: Int, view: View) {
                Toast.makeText(this@AlertActivity, "qwe", Toast.LENGTH_LONG).show()
            }
        }

        alertActionsAdapter.actions = alert.actions
    }

    override fun initPresenter(presenter: AlertPresenter) {
        presenter.view = this
        super.initPresenter(presenter)
    }

    override fun setContentUrl(url: String) {
        contentWebView.loadUrl(url)
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
        supportActionBar?.title = alert.title
    }
}