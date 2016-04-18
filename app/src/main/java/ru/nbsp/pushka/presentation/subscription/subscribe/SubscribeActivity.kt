package ru.nbsp.pushka.presentation.subscription.subscribe

import android.app.ProgressDialog
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import ru.nbsp.pushka.BaseApplication
import ru.nbsp.pushka.R
import ru.nbsp.pushka.presentation.PresentedActivity
import ru.nbsp.pushka.presentation.core.model.source.PresentationParam
import ru.nbsp.pushka.presentation.core.model.source.PresentationSource
import ru.nbsp.pushka.presentation.subscription.params.ParamsFragment
import ru.nbsp.pushka.util.ColorUtils
import ru.nbsp.pushka.util.IconUtils
import ru.nbsp.pushka.util.bindView
import javax.inject.Inject

class SubscribeActivity : PresentedActivity<SubscribePresenter>(), SubscribeView {

    companion object {
        const val ARG_SOURCE_ID = "arg_source"
        const val ARG_SOURCE_COLOR = "arg_source_color"
    }

    @Inject
    lateinit var presenter: SubscribePresenter

    @Inject
    lateinit var iconUtils: IconUtils

    @Inject
    lateinit var colorUtils: ColorUtils

    lateinit var fragment: ParamsFragment
    lateinit var sourceId: String
    lateinit var sourceColor: String

    val subscribeProgressDialog: ProgressDialog by lazy { ProgressDialog(this) }
    val sourceTitle: TextView by bindView(R.id.source_title)
    val subtitle: TextView by bindView(R.id.source_subtitle)
    val icon: ImageView by bindView(R.id.item_icon)
    val iconBackground: View by bindView(R.id.item_icon_background)
    val subscribeButton: Button by bindView(R.id.subscribe_button)
    val toolbar: Toolbar by bindView(R.id.toolbar)

    val withSoundContainer: ViewGroup by bindView(R.id.subscribe_with_sound_container)
    val withSoundCheckbox: CheckBox by bindView(R.id.subscribe_with_sound_checkbox)
    val withAlertContainer: ViewGroup by bindView(R.id.subscribe_with_alert_container)
    val withAlertCheckbox: CheckBox by bindView(R.id.subscribe_with_alert_checkbox)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subscribe)
        BaseApplication.graph.inject(this)

        initArgs()
        initToolbar()
        initColors()
        initFragment()
        initPresenter(presenter)
        initViews()

        presenter.loadSourceFromCache(sourceId)
    }

    private fun initFragment() {
        val fragmentManager = supportFragmentManager

        var cachedFragment: Fragment? = fragmentManager.findFragmentById(R.id.container)

        if (cachedFragment == null) {
            cachedFragment = ParamsFragment()
            fragmentManager.beginTransaction().replace(R.id.container, cachedFragment).commitAllowingStateLoss()
        }

        fragment = cachedFragment as ParamsFragment
    }

    private fun initViews() {
        subscribeProgressDialog.setMessage(resources.getString(R.string.subscribe_dialog_message))
        subscribeButton.setOnClickListener {
            presenter.subscribeButtonClicked(fragment.getValues())
        }

        withSoundContainer.setOnClickListener {
            withSoundCheckbox.isChecked = !withSoundCheckbox.isChecked
        }

        withAlertContainer.setOnClickListener {
            withAlertCheckbox.isChecked = !withAlertCheckbox.isChecked
        }
    }

    private fun initArgs() {
        sourceId = intent.extras.getString(ARG_SOURCE_ID)
        sourceColor = intent.extras.getString(ARG_SOURCE_COLOR)
    }

    private fun initColors() {
        toolbar.setBackgroundColor(Color.parseColor(sourceColor))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = colorUtils.darker(Color.parseColor(sourceColor))
        }
    }

    override fun setSourceData(source: PresentationSource) {
        sourceTitle.text = source.name
        subtitle.text = source.description
        (iconBackground.background as GradientDrawable)
                .setColor(Color.parseColor(source.color))
        icon.setImageResource(iconUtils.getIcon(source.icon))
    }

    override fun validateFields(): Boolean {
        return fragment.validate()
    }

    override fun setParams(params: List<PresentationParam>) {
        fragment.setParams(params)
    }

    override fun initPresenter(presenter: SubscribePresenter) {
        presenter.view = this
        super.initPresenter(presenter)
    }

    override fun setTitle(sourceTitle: String) {
        title = sourceTitle
    }

    override fun showSubscribeProgressDialog() {
        subscribeProgressDialog.show()
    }

    override fun hideSubscribeProgressDialog() {
        subscribeProgressDialog.dismiss()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            (supportActionBar as ActionBar).setDisplayHomeAsUpEnabled(true);
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()

        if (subscribeProgressDialog.isShowing) {
            subscribeProgressDialog.dismiss()
        }
    }
}
