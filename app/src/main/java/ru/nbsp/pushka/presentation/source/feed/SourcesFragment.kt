package ru.nbsp.pushka.presentation.source.feed

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.nbsp.pushka.BaseApplication
import ru.nbsp.pushka.R
import ru.nbsp.pushka.api.model.source.Source
import ru.nbsp.pushka.presentation.PresentedFragment
import ru.nbsp.pushka.presentation.core.widget.StateRecyclerView
import ru.nbsp.pushka.presentation.source.feed.adapter.SourcesAdapter
import ru.nbsp.pushka.util.bindView
import javax.inject.Inject

/**
 * Created by Dimorinny on 24.02.16.
 */
class SourcesFragment : PresentedFragment<SourcesPresenter>(), SourceView {

    val recyclerView: StateRecyclerView by bindView(R.id.sources_recycler_view)
    val refreshLayout: SwipeRefreshLayout by bindView(R.id.sources_refresh_layout)

    val emptyPlaceholder: View by bindView(R.id.empty_placeholder)
    val errorPlaceholder: View by bindView(R.id.error_placeholder)
    val progressPlaceholder: View by bindView(R.id.progress_placeholder)

    @Inject
    lateinit var presenter: SourcesPresenter

    lateinit var sourcesAdapter: SourcesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sources, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        BaseApplication.graph.inject(this)

        initViews()
        initPresenter(presenter)
        initRecyclerView()
        presenter.loadSourcesFromCache()
    }

    private fun initViews() {
        refreshLayout.setOnRefreshListener { refreshLayout.isRefreshing = false }
    }

    override fun initPresenter(presenter: SourcesPresenter) {
        presenter.view = this
        super.initPresenter(presenter)
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(activity)

        recyclerView.setEmptyView(emptyPlaceholder)
        recyclerView.setErrorView(errorPlaceholder)
        recyclerView.setProgressView(progressPlaceholder)
        recyclerView.setState(StateRecyclerView.State.STATE_PROGRESS)

        sourcesAdapter = SourcesAdapter()
        recyclerView.adapter = sourcesAdapter
    }

    override fun setSources(sources: List<Source>) {
        sourcesAdapter.sources = sources
        recyclerView.setState(if (sources.isEmpty()) StateRecyclerView.State.STATE_EMPTY else StateRecyclerView.State.STATE_NORMAL)
    }
}