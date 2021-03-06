package ru.nbsp.pushka.presentation.subscription.subscribe

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import ru.nbsp.pushka.BaseApplication
import ru.nbsp.pushka.R
import ru.nbsp.pushka.presentation.PresentedActivity
import ru.nbsp.pushka.presentation.core.model.source.PresentationParam
import ru.nbsp.pushka.presentation.core.model.source.PresentationSource
import ru.nbsp.pushka.presentation.core.state.State
import ru.nbsp.pushka.presentation.core.widget.StateFrameLayout
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

    val subscriptionStateLayout: StateFrameLayout by bindView(R.id.subscription_state_layout)
    val errorPlaceholder: View by bindView(R.id.error_placeholder)
    val progressPlaceholder: View by bindView(R.id.progress_placeholder)
    val sourceContainer: ViewGroup by bindView(R.id.source_container)

    val subscribeProgressDialog: ProgressDialog by lazy { ProgressDialog(this) }
    val sourceTitle: TextView by bindView(R.id.source_title)
    val subtitle: TextView by bindView(R.id.source_subtitle)
    val icon: ImageView by bindView(R.id.item_icon)
    val iconBackground: View by bindView(R.id.item_icon_background)
    val subscribeButton: Button by bindView(R.id.subscribe_button)
    val toolbar: Toolbar by bindView(R.id.toolbar)

    val coordinatorContainer: CoordinatorLayout by bindView(R.id.coordinator_container)
    val withSoundContainer: ViewGroup by bindView(R.id.subscribe_with_sound_container)
    val withSoundCheckbox: CheckBox by bindView(R.id.subscribe_with_sound_checkbox)
    val withAlertContainer: ViewGroup by bindView(R.id.subscribe_with_alert_container)
    val withAlertCheckbox: CheckBox by bindView(R.id.subscribe_with_alert_checkbox)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subscribe)
        BaseApplication.graph.inject(this)

        initArgs()
        initStateLayout()
        initToolbar()
        initColors()
        initFragment()
        initPresenter(presenter)
        initViews()

        presenter.loadSourceFromCache(sourceId)
        presenter.loadSourceFromServer(sourceId)
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
            presenter.subscribeButtonClicked(withSoundCheckbox.isChecked, withAlertCheckbox.isChecked, fragment.getValues())
        }

        withSoundContainer.setOnClickListener {
            withSoundCheckbox.isChecked = !withSoundCheckbox.isChecked
        }

        withAlertContainer.setOnClickListener {
            withAlertCheckbox.isChecked = !withAlertCheckbox.isChecked
        }
    }

    private fun initStateLayout() {
        subscriptionStateLayout.setNormalView(sourceContainer)
        subscriptionStateLayout.setErrorView(errorPlaceholder)
        subscriptionStateLayout.setProgressView(progressPlaceholder)
        subscriptionStateLayout.setState(State.STATE_PROGRESS)
    }

    private fun initArgs() {
        sourceId = intent.getStringExtra(ARG_SOURCE_ID)
        sourceColor = intent.getStringExtra(ARG_SOURCE_COLOR)
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

    override fun setState(state: State) {
        subscriptionStateLayout.setState(state)
    }

    override fun setParams(params: List<PresentationParam>) {
        fragment.setParams(params)
    }

    override fun initPresenter(presenter: SubscribePresenter) {
        presenter.view = this
        super.initPresenter(presenter)
    }

    override fun showSubscribeProgressDialog() {
        subscribeProgressDialog.show()
    }

    override fun hideSubscribeProgressDialog() {
        subscribeProgressDialog.dismiss()
    }

    override fun showShareMenu(subject: String, text: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, text)
        startActivity(Intent.createChooser(intent, getString(R.string.app_name)))
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            (supportActionBar as ActionBar).setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun showError(message: String) {
        Snackbar.make(coordinatorContainer, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun showSubscribeConnectionError(message: String) {
        Snackbar.make(coordinatorContainer, message, Snackbar.LENGTH_SHORT).setAction(getString(R.string.snack_retry), {
            presenter.subscribeButtonClicked(withSoundCheckbox.isChecked, withAlertCheckbox.isChecked, fragment.getValues())
        }).show()
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun closeScreen() {
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.action_share -> {
                presenter.sharedButtonClicked()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.share, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onDestroy() {
        super.onDestroy()

        if (subscribeProgressDialog.isShowing) {
            subscribeProgressDialog.dismiss()
        }
    }
}
