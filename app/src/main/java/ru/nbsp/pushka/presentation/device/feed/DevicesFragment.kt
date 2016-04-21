package ru.nbsp.pushka.presentation.device.feed

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.nbsp.pushka.BaseApplication
import ru.nbsp.pushka.R
import ru.nbsp.pushka.presentation.PresentedFragment
import ru.nbsp.pushka.presentation.core.model.device.PresentationDevice
import ru.nbsp.pushka.presentation.core.state.State
import ru.nbsp.pushka.presentation.core.widget.StateRecyclerView
import ru.nbsp.pushka.presentation.device.feed.adapter.DevicesAdapter
import ru.nbsp.pushka.util.DeviceTokenUtils
import ru.nbsp.pushka.util.bindView
import javax.inject.Inject

/**
 * Created by Dimorinny on 31.03.16.
 */
class DevicesFragment : PresentedFragment<DevicesPresenter>(), DevicesView {

    val recyclerView: StateRecyclerView by bindView(R.id.devices_recycler_view)
    val refreshLayout: SwipeRefreshLayout by bindView(R.id.devices_refresh_layout)

    val emptyPlaceholder: View by bindView(R.id.empty_placeholder)
    val errorPlaceholder: View by bindView(R.id.error_placeholder)
    val progressPlaceholder: View by bindView(R.id.progress_placeholder)

    @Inject
    lateinit var presenter: DevicesPresenter

    @Inject
    lateinit var deviceTokenUtils: DeviceTokenUtils

    lateinit var devicesAdapter: DevicesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_devices, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        BaseApplication.graph.inject(this)

        initViews()
        initPresenter(presenter)
        initRecyclerView()

        presenter.loadDevicesFromServer()
        presenter.loadDevicesFromCache()
    }

    private fun initViews() {
        refreshLayout.setOnRefreshListener { presenter.loadDevicesFromServer() }

        refreshLayout.setColorSchemeResources(R.color.green,
                R.color.blue,
                R.color.orange);
    }

    override fun initPresenter(presenter: DevicesPresenter) {
        presenter.view = this
        super.initPresenter(presenter)
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(activity)

        recyclerView.setEmptyView(emptyPlaceholder)
        recyclerView.setErrorView(errorPlaceholder)
        recyclerView.setProgressView(progressPlaceholder)
        recyclerView.setState(State.STATE_PROGRESS)

        devicesAdapter = DevicesAdapter(deviceTokenUtils)
        recyclerView.adapter = devicesAdapter
    }

    override fun disableSwipeRefresh() {
        refreshLayout.isRefreshing = false
    }

    override fun setDevices(devices: List<PresentationDevice>) {
        devicesAdapter.devices = devices
    }

    override fun setState(state: State) {
        recyclerView.setState(state)
    }
}