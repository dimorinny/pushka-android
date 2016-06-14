package ru.nbsp.pushka.presentation.category.feed

import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.nbsp.pushka.BaseApplication
import ru.nbsp.pushka.R
import ru.nbsp.pushka.presentation.PresentedFragment
import ru.nbsp.pushka.presentation.category.feed.adapter.CategoriesAdapter
import ru.nbsp.pushka.presentation.core.adapter.OnItemClickListener
import ru.nbsp.pushka.presentation.core.model.source.PresentationCategory
import ru.nbsp.pushka.presentation.core.state.State
import ru.nbsp.pushka.presentation.core.widget.StateFrameLayout
import ru.nbsp.pushka.presentation.source.feed.SourcesActivity
import ru.nbsp.pushka.util.ColorUtils
import ru.nbsp.pushka.util.IconUtils
import ru.nbsp.pushka.util.bindView
import javax.inject.Inject

/**
 * Created by Dimorinny on 08.03.16.
 */
class CategoriesFragment : PresentedFragment<CategoriesPresenter>(), CategoriesView {

    val recyclerView: RecyclerView by bindView(R.id.categories_recycler_view)
    val refreshLayout: SwipeRefreshLayout by bindView(R.id.categories_refresh_layout)
    val container: ViewGroup by bindView(R.id.categories_container)

    val stateLayout: StateFrameLayout by bindView(R.id.categories_state_layout)
    val emptyPlaceholder: View by bindView(R.id.empty_placeholder)
    val errorPlaceholder: View by bindView(R.id.error_placeholder)
    val progressPlaceholder: View by bindView(R.id.progress_placeholder)

    @Inject
    lateinit var presenter: CategoriesPresenter

    @Inject
    lateinit var colorUtils: ColorUtils

    @Inject
    lateinit var iconUtils: IconUtils

    lateinit var adapter: CategoriesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_categories, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        BaseApplication.graph.inject(this)

        initViews()
        initStateLayout()
        initPresenter(presenter)
        initRecyclerView()
        presenter.loadCategoriesFromCache()
        presenter.loadCategoriesFromServer()
    }

    private fun initViews() {
        refreshLayout.setOnRefreshListener { presenter.loadCategoriesFromServer() }

        refreshLayout.setColorSchemeResources(R.color.green,
                R.color.blue,
                R.color.orange);
    }

    private fun initStateLayout() {
        stateLayout.setEmptyView(emptyPlaceholder)
        stateLayout.setErrorView(errorPlaceholder)
        stateLayout.setProgressView(progressPlaceholder)
        stateLayout.setNormalView(container)
        stateLayout.setState(State.STATE_PROGRESS)
    }

    override fun initPresenter(presenter: CategoriesPresenter) {
        presenter.view = this
        super.initPresenter(presenter)
    }

    private fun initRecyclerView() {
        adapter = CategoriesAdapter(iconUtils, colorUtils)
        adapter.itemClickListener = object : OnItemClickListener {
            override fun onItemClicked(index: Int, view: View) {
                presenter.onCategoryClicked(index)
            }
        }

        recyclerView.adapter = adapter
    }

    override fun disableSwipeRefresh() {
        refreshLayout.isRefreshing = false
    }

    override fun setCategories(categories: List<PresentationCategory>) {
        adapter.categories = categories
    }

    override fun setState(state: State) {
        stateLayout.setState(state)
    }

    override fun openCategoryScreen(presentationCategory: PresentationCategory) {
        val intent = Intent(activity, SourcesActivity::class.java)
        intent.putExtra(SourcesActivity.ARG_CATEGORY, presentationCategory)
        startActivity(intent)
    }
}