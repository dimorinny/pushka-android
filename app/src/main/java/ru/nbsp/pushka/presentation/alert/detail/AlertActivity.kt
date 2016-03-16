package ru.nbsp.pushka.presentation.alert.detail

import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import ru.nbsp.pushka.BaseApplication
import ru.nbsp.pushka.R
import ru.nbsp.pushka.presentation.PresentedActivity
import ru.nbsp.pushka.presentation.core.model.alert.PresentationAlert
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
    val collapsingToolbar: CollapsingToolbarLayout by bindView(R.id.collapsing_toolbar)

    @Inject
    lateinit var presenter: AlertPresenter
    lateinit var alert: PresentationAlert

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

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            (supportActionBar as ActionBar).setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbar.title = alert.title
    }
}