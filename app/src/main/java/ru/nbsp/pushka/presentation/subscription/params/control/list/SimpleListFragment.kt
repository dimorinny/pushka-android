package ru.nbsp.pushka.presentation.subscription.params.control

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import ru.nbsp.pushka.BaseApplication
import ru.nbsp.pushka.R
import ru.nbsp.pushka.presentation.PresentedFragment
import ru.nbsp.pushka.presentation.alert.feed.AlertsPresenter
import ru.nbsp.pushka.presentation.alert.feed.adapter.AlertsAdapter
import ru.nbsp.pushka.presentation.alert.feed.adapter.SimpleListAdapter
import ru.nbsp.pushka.presentation.core.adapter.OnItemClickListener
import ru.nbsp.pushka.presentation.core.base.BaseFragment
import ru.nbsp.pushka.presentation.core.model.alert.PresentationAlert
import ru.nbsp.pushka.presentation.core.model.source.control.SimpleListControlItem
import ru.nbsp.pushka.presentation.core.state.State
import ru.nbsp.pushka.presentation.core.widget.StateRecyclerView
import ru.nbsp.pushka.util.bindView
import rx.Subscriber
import java.util.*
import javax.inject.Inject

/**
 * Created by egor on 20.03.16.
 */
class SimpleListFragment: PresentedFragment<SimpleListPresenter>(), SimpleListView {
    override fun setState(state: State) {
        recyclerView.setState(state)
    }

    override fun setItems(items: List<SimpleListControlItem>) {
        adapter.items = items
    }

    @Inject
    lateinit var presenter: SimpleListPresenter

    val recyclerView: StateRecyclerView by bindView(R.id.simple_list_recycler_view)
    val emptyPlaceholder: View by bindView(R.id.empty_placeholder)
    val errorPlaceholder: View by bindView(R.id.error_placeholder)
    val progressPlaceholder: View by bindView(R.id.progress_placeholder)
    lateinit var adapter: SimpleListAdapter


    // todo swipe


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        BaseApplication.graph.inject(this)

        initRecyclerView()
        initPresenter(presenter)
    }

    override fun initPresenter(presenter: SimpleListPresenter) {
        presenter.view = this
        super.initPresenter(presenter)
    }

    val EXTRA_SELECTED_ITEM = "extra_selected_item"

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(activity)

        recyclerView.setEmptyView(emptyPlaceholder)
        recyclerView.setErrorView(errorPlaceholder)
        recyclerView.setProgressView(progressPlaceholder)
        recyclerView.setState(State.STATE_PROGRESS)

        adapter = SimpleListAdapter()
        recyclerView.adapter = adapter

        adapter.itemClickListener = object : OnItemClickListener {
            override fun onItemClicked(index: Int, view: View) {
                presenter.onAlertClicked(index)

            }
        }
    }

    override fun returnResult(result: String) {
        var data = Intent()

        data.putExtra(EXTRA_SELECTED_ITEM, result)
        activity.setResult(Activity.RESULT_OK, data)
        activity.finish()
    }


}