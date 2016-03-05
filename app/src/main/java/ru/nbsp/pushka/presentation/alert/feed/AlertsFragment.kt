package ru.nbsp.pushka.presentation.alert.feed

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import ru.nbsp.pushka.BaseApplication
import ru.nbsp.pushka.R
import ru.nbsp.pushka.data.model.alert.Alert
import ru.nbsp.pushka.presentation.PresentedFragment
import ru.nbsp.pushka.presentation.alert.feed.adapter.AlertsAdapter
import ru.nbsp.pushka.presentation.core.adapter.OnItemClickListener
import ru.nbsp.pushka.presentation.core.state.State
import ru.nbsp.pushka.presentation.core.state.StateManager
import ru.nbsp.pushka.presentation.core.widget.StateRecyclerView
import ru.nbsp.pushka.util.bindView
import javax.inject.Inject

/**
 * Created by Dimorinny on 12.02.16.
 */
class AlertsFragment : PresentedFragment<AlertsPresenter>(), AlertsView {

    val recyclerView: StateRecyclerView by bindView(R.id.alerts_recycler_view)
    val refreshLayout: SwipeRefreshLayout by bindView(R.id.alerts_refresh_layout)

    val emptyPlaceholder: View by bindView(R.id.empty_placeholder)
    val errorPlaceholder: View by bindView(R.id.error_placeholder)
    val progressPlaceholder: View by bindView(R.id.progress_placeholder)

    @Inject
    lateinit var presenter: AlertsPresenter

    @Inject
    lateinit var picasso: Picasso

    lateinit var alertsAdapter: AlertsAdapter
    var toolbarStateManager: StateManager? = null

    override fun onAttach(context: Context?) {
        toolbarStateManager = activity as StateManager
        super.onAttach(context)
    }

    override fun onDetach() {
        toolbarStateManager = null
        super.onDetach()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_alerts, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        BaseApplication.graph.inject(this)

        initViews()
        initPresenter(presenter)
        initRecyclerView()
        presenter.loadAlertsFromCache()
        presenter.loadAlertsFromServer()
    }

    private fun initViews() {
        refreshLayout.setOnRefreshListener { refreshLayout.isRefreshing = false }
    }

    override fun initPresenter(presenter: AlertsPresenter) {
        presenter.view = this
        super.initPresenter(presenter)
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(activity)

        recyclerView.setEmptyView(emptyPlaceholder)
        recyclerView.setErrorView(errorPlaceholder)
        recyclerView.setProgressView(progressPlaceholder)
        recyclerView.setState(State.STATE_PROGRESS)

        alertsAdapter = AlertsAdapter(picasso)
        recyclerView.adapter = alertsAdapter

        alertsAdapter.itemClickListener = object : OnItemClickListener {
            override fun onItemClicked(index: Int, view: View) {
                presenter.onAlertClicked(index)
            }
        }
    }

    override fun setAlerts(alerts: List<Alert>) {
        alertsAdapter.alerts = alerts
    }

    override fun setState(state: State) {
        recyclerView.setState(state)
    }

    override fun setToolbarState(state: State) {
        toolbarStateManager?.setState(state)
    }

    override fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }
}