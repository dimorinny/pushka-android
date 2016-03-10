package ru.nbsp.pushka.presentation.category.feed

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import ru.nbsp.pushka.BaseApplication
import ru.nbsp.pushka.R
import ru.nbsp.pushka.presentation.PresentedFragment
import ru.nbsp.pushka.presentation.category.feed.adapter.CategoriesAdapter
import ru.nbsp.pushka.presentation.core.adapter.OnItemClickListener
import ru.nbsp.pushka.presentation.core.model.source.PresentationCategory
import ru.nbsp.pushka.presentation.core.state.State
import ru.nbsp.pushka.presentation.core.widget.GridStateRecyclerView
import ru.nbsp.pushka.presentation.source.feed.SourcesActivity
import ru.nbsp.pushka.util.bindView
import javax.inject.Inject

/**
 * Created by Dimorinny on 08.03.16.
 */
class CategoriesFragment : PresentedFragment<CategoriesPresenter>(), CategoriesView {

    val recyclerView: GridStateRecyclerView by bindView(R.id.categories_recycler_view)
    val refreshLayout: SwipeRefreshLayout by bindView(R.id.categories_refresh_layout)

    val emptyPlaceholder: View by bindView(R.id.empty_placeholder)
    val errorPlaceholder: View by bindView(R.id.error_placeholder)
    val progressPlaceholder: View by bindView(R.id.progress_placeholder)

    @Inject
    lateinit var presenter: CategoriesPresenter

    @Inject
    lateinit var picasso: Picasso

    lateinit var adapter: CategoriesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_categories, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        BaseApplication.graph.inject(this)

        initViews()
        initPresenter(presenter)
        initRecyclerView()
        presenter.loadCategoriesFromCache()
    }

    private fun initViews() {
        refreshLayout.setOnRefreshListener {
            presenter.loadCategoriesFromCache()
            refreshLayout.isRefreshing = false
        }
    }

    override fun initPresenter(presenter: CategoriesPresenter) {
        presenter.view = this
        super.initPresenter(presenter)
    }

    private fun initRecyclerView() {
        recyclerView.setEmptyView(emptyPlaceholder)
        recyclerView.setErrorView(errorPlaceholder)
        recyclerView.setProgressView(progressPlaceholder)
        recyclerView.setState(State.STATE_PROGRESS)

        adapter = CategoriesAdapter(picasso)
        adapter.itemClickListener = object : OnItemClickListener {
            override fun onItemClicked(index: Int, view: View) {
                // TODO: think about passing view over presenter
//                presenter.onCategoryClicked(index)
                openCategoryScreen(view)
            }
        }

        recyclerView.adapter = adapter
    }

    override fun setCategories(categories: List<PresentationCategory>) {
        adapter.categories = categories
        // TODO: think about starting animation for every setting data
        recyclerView.scheduleLayoutAnimation()
    }

    override fun setState(state: State) {
        recyclerView.setState(state)
    }

    override fun openCategoryScreen() {
        val intent = Intent(activity, SourcesActivity::class.java)
        startActivity(intent)
    }

    fun openCategoryScreen(view: View) {
        startCategoryActivity(view)
    }

    fun startCategoryActivity(image: View) {
        val intent = Intent(activity, SourcesActivity::class.java)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, image, image.transitionName)
            ActivityCompat.startActivity(activity, intent, activityOptions.toBundle())
        } else {
            startActivity(intent)
        }
    }
}