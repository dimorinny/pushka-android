package ru.nbsp.pushka.presentation.source.feed

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.nbsp.pushka.BaseApplication
import ru.nbsp.pushka.R
import ru.nbsp.pushka.presentation.PresentedFragment
import ru.nbsp.pushka.presentation.core.adapter.OnItemClickListener
import ru.nbsp.pushka.presentation.core.model.source.PresentationCategory
import ru.nbsp.pushka.presentation.core.model.source.PresentationSource
import ru.nbsp.pushka.presentation.core.state.State
import ru.nbsp.pushka.presentation.core.widget.StateRecyclerView
import ru.nbsp.pushka.presentation.source.feed.adapter.SourcesAdapter
import ru.nbsp.pushka.presentation.subscription.subscribe.SubscribeActivity
import ru.nbsp.pushka.util.IconUtils
import ru.nbsp.pushka.util.bindView
import javax.inject.Inject

/**
 * Created by Dimorinny on 24.02.16.
 */
class SourcesFragment : PresentedFragment<SourcesPresenter>(), SourceView {

    companion object {
        const val ARG_CATEGORY = "arg_category"
    }

    val recyclerView: StateRecyclerView by bindView(R.id.sources_recycler_view)

    val emptyPlaceholder: View by bindView(R.id.empty_placeholder)
    val errorPlaceholder: View by bindView(R.id.error_placeholder)
    val progressPlaceholder: View by bindView(R.id.progress_placeholder)

    @Inject
    lateinit var presenter: SourcesPresenter

    @Inject
    lateinit var iconUtils: IconUtils

    lateinit var adapter: SourcesAdapter
    lateinit var category: PresentationCategory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sources, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        BaseApplication.graph.inject(this)

        initArguments()
        initPresenter(presenter)
        initRecyclerView()
        presenter.loadSourcesFromServer()
        presenter.loadSourcesFromCache()
    }

    private fun initArguments() {
        if (arguments.getSerializable(ARG_CATEGORY) != null) {
            category = arguments.getSerializable(ARG_CATEGORY) as PresentationCategory
        }
    }

    override fun initPresenter(presenter: SourcesPresenter) {
        presenter.view = this
        presenter.category = category
        super.initPresenter(presenter)
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(activity)

        recyclerView.setEmptyView(emptyPlaceholder)
        recyclerView.setErrorView(errorPlaceholder)
        recyclerView.setProgressView(progressPlaceholder)
        recyclerView.setState(State.STATE_PROGRESS)

        adapter = SourcesAdapter(iconUtils)
        adapter.itemClickListener = object : OnItemClickListener {
            override fun onItemClicked(index: Int, view: View) {
                presenter.onSourceClicked(index)
            }
        }

        recyclerView.adapter = adapter
    }

    override fun openSubscribeScreen(source: PresentationSource) {
        val intent = Intent(activity, SubscribeActivity::class.java)
        intent.putExtra(SubscribeActivity.ARG_SOURCE_ID, source.id)
        intent.putExtra(SubscribeActivity.ARG_SOURCE_COLOR, source.color)
        startActivity(intent)
    }

    override fun setSources(sources: List<PresentationSource>) {
        adapter.sources = sources
    }

    override fun setState(state: State) {
        recyclerView.setState(state)
    }
}