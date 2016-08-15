package ru.nbsp.pushka.presentation.alert.feed

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.squareup.picasso.Picasso
import ru.nbsp.pushka.BaseApplication
import ru.nbsp.pushka.R
import ru.nbsp.pushka.presentation.PresentedFragment
import ru.nbsp.pushka.presentation.alert.detail.AlertActivity
import ru.nbsp.pushka.presentation.alert.feed.adapter.AlertsAdapter
import ru.nbsp.pushka.presentation.category.feed.CategoriesActivity
import ru.nbsp.pushka.presentation.core.adapter.OnItemClickListener
import ru.nbsp.pushka.presentation.core.model.alert.PresentationAlert
import ru.nbsp.pushka.presentation.core.state.State
import ru.nbsp.pushka.presentation.core.widget.StateFrameLayout
import ru.nbsp.pushka.util.IconUtils
import ru.nbsp.pushka.util.StringUtils
import ru.nbsp.pushka.util.bindView
import javax.inject.Inject

/**
 * Created by Dimorinny on 12.02.16.
 */
class AlertsFragment : PresentedFragment<AlertsPresenter>(), AlertsView {

    var alertsActivityCallback: AlertsActivityCallback? = null

    val container: ViewGroup by bindView(R.id.alerts_container)
    val recyclerView: RecyclerView by bindView(R.id.alerts_recycler_view)
    val refreshLayout: SwipeRefreshLayout by bindView(R.id.alerts_refresh_layout)

    val stateLayout: StateFrameLayout by bindView(R.id.alerts_state_layout)
    val emptyPlaceholder: View by bindView(R.id.empty_placeholder)
    val errorPlaceholder: View by bindView(R.id.error_placeholder)
    val progressPlaceholder: View by bindView(R.id.progress_placeholder)

    val emptyTitle: TextView by bindView(R.id.one_action_title)
    val emptySubtitle: TextView by bindView(R.id.one_action_subtitle)
    val emptyButton: TextView by bindView(R.id.one_action_button)

    @Inject
    lateinit var presenter: AlertsPresenter

    @Inject
    lateinit var picasso: Picasso

    @Inject
    lateinit var iconUtils: IconUtils

    @Inject
    lateinit var stringUtils: StringUtils

    lateinit var alertsAdapter: AlertsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_alerts, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        BaseApplication.graph.inject(this)

        initPresenter(presenter)
        initViews()
        initStateLayout()
        initRecyclerView()
        presenter.loadAlertsFromCache()
        presenter.loadAlertsFromServer()
    }

    fun loadAlertsFromServer() {
        presenter.loadAlertsFromServer()
    }

    private fun initViews() {
        emptyTitle.text = stringUtils.getString(R.string.alert_empty_placeholder_title)
        emptySubtitle.text = stringUtils.getString(R.string.alert_empty_placeholder_subtitle)
        emptyButton.text = stringUtils.getString(R.string.alert_empty_placeholder_action)
        emptyButton.setOnClickListener { openCategoriesScreen() }

        refreshLayout.setColorSchemeResources(R.color.green,
                R.color.blue,
                R.color.orange)

        refreshLayout.setOnRefreshListener { presenter.loadAlertsFromServer() }
    }

    private fun initStateLayout() {
        stateLayout.setEmptyView(emptyPlaceholder)
        stateLayout.setErrorView(errorPlaceholder)
        stateLayout.setProgressView(progressPlaceholder)
        stateLayout.setNormalView(container)
        stateLayout.setState(State.STATE_PROGRESS)
    }

    override fun initPresenter(presenter: AlertsPresenter) {
        presenter.view = this
        super.initPresenter(presenter)
    }

    private fun initRecyclerView() {
        alertsAdapter = AlertsAdapter(context, picasso, iconUtils)

        recyclerView.adapter = alertsAdapter

//        initAttacher()
        alertsAdapter.itemClickListener = object : OnItemClickListener {
            override fun onItemClicked(index: Int, view: View) {
                presenter.onAlertClicked(index)
            }
        }
    }

//    private fun initAttacher() {
//        val attacher = Mugen.with(recyclerView, object : MugenCallbacks {
//            override fun onLoadMore() {
//                alertsAdapter.isProgress = true
//                presenter.loadMoreAlertsFromServer()
//            }
//
//            override fun hasLoadedAllItems(): Boolean {
//                return presenter.hasLoadedAllAlerts
//            }
//
//            override fun isLoading(): Boolean {
//                return presenter.isAlertsLoading
//            }
//        }).start()
//
//        attacher.isLoadMoreEnabled = true
//    }

    override fun setAlerts(alerts: List<PresentationAlert>) {
        alertsAdapter.alerts = alerts
    }

    override fun setState(state: State) {
        stateLayout.setState(state)
    }

    override fun disableSwipeRefresh() {
        refreshLayout.isRefreshing = false
    }

    override fun openAlertScreen(alert: PresentationAlert) {
        val intent = Intent(activity, AlertActivity::class.java)
        intent.putExtra(AlertActivity.ARG_ALERT_ID, alert.id)
        activity.startActivity(intent)
    }

    override fun showMessage(message: String) {
        alertsActivityCallback?.showMessage(message)
    }

    override fun showLoadConnectionAlertsError(message: String) {
        alertsActivityCallback?.showLoadAlertsConnectionError(message)
    }

    private fun openCategoriesScreen() {
        val intent = Intent(activity, CategoriesActivity::class.java)
        activity.startActivity(intent)
    }

    fun onSearchQueryChanged(query: String) {
        presenter.onSearchQueryChanged(query)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onAttach(context: Context) {
        alertsActivityCallback = activity as AlertsActivityCallback
        super.onAttach(context)
    }

    override fun onDetach() {
        alertsActivityCallback = null
        super.onDetach()
    }
}