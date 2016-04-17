package ru.nbsp.pushka.presentation.device.feed.container

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.gordonwong.materialsheetfab.DimOverlayFrameLayout
import com.gordonwong.materialsheetfab.MaterialSheetFab
import com.gordonwong.materialsheetfab.MaterialSheetFabEventListener
import ru.nbsp.pushka.BaseApplication
import ru.nbsp.pushka.R
import ru.nbsp.pushka.presentation.core.base.OneFragmentNavigationActivity
import ru.nbsp.pushka.presentation.core.model.device.PresentationDeviceType
import ru.nbsp.pushka.presentation.core.widget.AnimatedFloatingActionButton
import ru.nbsp.pushka.presentation.device.feed.DevicesFragment
import ru.nbsp.pushka.presentation.device.feed.container.adapter.DeviceTypesAdapter
import ru.nbsp.pushka.util.bindView
import javax.inject.Inject

/**
 * Created by Dimorinny on 18.04.16.
 */
class DevicesActivity : OneFragmentNavigationActivity(), DevicesContainerView {

    override fun getContentLayout(): Int {
        return R.layout.activity_devices
    }

    override fun getDrawerItemId(): Int {
        return R.id.drawer_devices
    }

    override fun createFragment(): Fragment {
        return DevicesFragment()
    }

    override fun injectActivity() {
        BaseApplication.graph.inject(this)
    }

    val devicesFab: AnimatedFloatingActionButton by bindView(R.id.devices_fab)
    val overlay: DimOverlayFrameLayout by bindView(R.id.overlay)
    val sheetLayout: CardView by bindView(R.id.devices_fab_sheet)
    val devicesRecyclerView: RecyclerView by bindView(R.id.devices_recycler_view)

    @Inject
    lateinit var devicesPresenter: DevicesContainerPresenter
    lateinit var devicesSheetFab: MaterialSheetFab<AnimatedFloatingActionButton>
    lateinit var devicesAdapter: DeviceTypesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViews()
        initPresenter(devicesPresenter)
    }

    fun initViews() {
        devicesSheetFab = MaterialSheetFab(devicesFab, sheetLayout, overlay,
                ContextCompat.getColor(this, R.color.white), ContextCompat.getColor(this, R.color.colorPrimary))
        devicesSheetFab.setEventListener(object : MaterialSheetFabEventListener() {

            var statusBarColor: Int? = null

            override fun onShowSheet() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    statusBarColor = window.statusBarColor
                    window.statusBarColor = ContextCompat.getColor(this@DevicesActivity, R.color.colorPrimaryDark2)
                }
            }

            override fun onHideSheet() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.statusBarColor = statusBarColor!!
                }
            }
        })

        initDevicesFabRecyclerView()
    }

    private fun initDevicesFabRecyclerView() {
        devicesRecyclerView.layoutManager = LinearLayoutManager(this)

        devicesAdapter = DeviceTypesAdapter(this)
        devicesAdapter.deviceTypeClickListener = object : DeviceTypesAdapter.OnDeviceTypeItemClickListener {
            override fun onItemClicked(deviceType: PresentationDeviceType) {
                devicesPresenter.onCreateDeviceItemClicked(deviceType)
            }
        }
        devicesRecyclerView.adapter = devicesAdapter
    }

    override fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

    override fun setDeviceTypes(deviceTypes: List<PresentationDeviceType>) {
        devicesAdapter.deviceTypes = deviceTypes
    }

    override fun onBackPressed() {
        if (devicesSheetFab.isSheetVisible) {
            devicesSheetFab.hideSheet();
        } else {
            super.onBackPressed()
        }
    }

    fun initPresenter(presenter: DevicesContainerPresenter) {
        devicesPresenter = presenter
        devicesPresenter.view = this
        devicesPresenter.onCreate()
    }

    override fun onResume() {
        super.onResume()
        devicesPresenter.onResume()
    }

    override fun onPause() {
        super.onPause()
        devicesPresenter.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        devicesPresenter.onDestroy()
    }
}