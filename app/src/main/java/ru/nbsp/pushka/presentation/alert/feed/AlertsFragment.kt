package ru.nbsp.pushka.presentation.alert.feed

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import ru.nbsp.pushka.BaseApplication
import ru.nbsp.pushka.R
import ru.nbsp.pushka.network.model.alert.Alert
import ru.nbsp.pushka.presentation.PresentedFragment
import ru.nbsp.pushka.presentation.alert.feed.adapter.AlertsAdapter
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
        recyclerView.setState(StateRecyclerView.State.STATE_PROGRESS)

        alertsAdapter = AlertsAdapter(picasso)
        recyclerView.adapter = alertsAdapter
    }

    override fun setAlerts(alerts: List<Alert>) {
        alertsAdapter.alerts = alerts
        recyclerView.setState(if (alerts.isEmpty()) StateRecyclerView.State.STATE_EMPTY else StateRecyclerView.State.STATE_NORMAL)
    }
}