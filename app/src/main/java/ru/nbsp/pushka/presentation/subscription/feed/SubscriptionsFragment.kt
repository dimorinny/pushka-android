package ru.nbsp.pushka.presentation.subscription.feed

import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ru.nbsp.pushka.BaseApplication
import ru.nbsp.pushka.R
import ru.nbsp.pushka.presentation.PresentedFragment
import ru.nbsp.pushka.presentation.category.feed.CategoriesActivity
import ru.nbsp.pushka.presentation.core.model.subscription.PresentationSubscription
import ru.nbsp.pushka.presentation.core.state.State
import ru.nbsp.pushka.presentation.core.widget.StateRecyclerView
import ru.nbsp.pushka.presentation.subscription.feed.adapter.SubscriptionsAdapter
import ru.nbsp.pushka.util.IconUtils
import ru.nbsp.pushka.util.StringUtils
import ru.nbsp.pushka.util.bindView
import javax.inject.Inject

/**
 * Created by Dimorinny on 26.02.16.
 */
class SubscriptionsFragment : PresentedFragment<SubscriptionsPresenter>(), SubscriptionsView {

    val recyclerView: StateRecyclerView by bindView(R.id.subscriptions_recycler_view)
    val refreshLayout: SwipeRefreshLayout by bindView(R.id.subscriptions_refresh_layout)

    val emptyPlaceholder: View by bindView(R.id.empty_placeholder)
    val errorPlaceholder: View by bindView(R.id.error_placeholder)
    val progressPlaceholder: View by bindView(R.id.progress_placeholder)

    val emptyTitle: TextView by bindView(R.id.one_action_title)
    val emptySubtitle: TextView by bindView(R.id.one_action_subtitle)
    val emptyButton: TextView by bindView(R.id.one_action_button)

    @Inject
    lateinit var presenter: SubscriptionsPresenter

    @Inject
    lateinit var iconUtils: IconUtils

    @Inject
    lateinit var stringUtils: StringUtils

    lateinit var subscriptionsAdapter: SubscriptionsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_subscriptions, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        BaseApplication.graph.inject(this)

        initViews()
        initPresenter(presenter)
        initRecyclerView()

        presenter.loadSubscriptionsFromServer()
        presenter.loadSubscriptionsFromCache()
    }

    private fun initViews() {
        emptyTitle.text = stringUtils.getString(R.string.subscription_empty_placeholder_title)
        emptySubtitle.text = stringUtils.getString(R.string.subscription_empty_placeholder_subtitle)
        emptyButton.text = stringUtils.getString(R.string.subscription_empty_placeholder_action)
        emptyButton.setOnClickListener { openCategoriesScreen() }

        refreshLayout.setOnRefreshListener { refreshLayout.isRefreshing = false }
    }

    override fun initPresenter(presenter: SubscriptionsPresenter) {
        presenter.view = this
        super.initPresenter(presenter)
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(activity)

        recyclerView.setEmptyView(emptyPlaceholder)
        recyclerView.setErrorView(errorPlaceholder)
        recyclerView.setProgressView(progressPlaceholder)
        recyclerView.setState(State.STATE_PROGRESS)

        subscriptionsAdapter = SubscriptionsAdapter(iconUtils)
        recyclerView.adapter = subscriptionsAdapter
    }

    override fun setSubscriptions(subscriptions: List<PresentationSubscription>) {
        subscriptionsAdapter.subscriptions = subscriptions
        recyclerView.setState(if (subscriptions.isEmpty()) State.STATE_EMPTY else State.STATE_NORMAL)
    }

    override fun setState(state: State) {
        recyclerView.setState(state)
    }

    private fun openCategoriesScreen() {
        val intent = Intent(activity, CategoriesActivity::class.java)
        activity.startActivity(intent)
    }
}