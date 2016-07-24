package ru.nbsp.pushka.presentation.subscription.edit

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
import android.widget.*
import ru.nbsp.pushka.BaseApplication
import ru.nbsp.pushka.R
import ru.nbsp.pushka.presentation.PresentedActivity
import ru.nbsp.pushka.presentation.core.model.source.PresentationParam
import ru.nbsp.pushka.presentation.core.model.subscription.PresentationSubscription
import ru.nbsp.pushka.presentation.core.state.State
import ru.nbsp.pushka.presentation.core.widget.StateFrameLayout
import ru.nbsp.pushka.presentation.subscription.params.ParamsFragment
import ru.nbsp.pushka.util.ColorUtils
import ru.nbsp.pushka.util.IconUtils
import ru.nbsp.pushka.util.bindView
import javax.inject.Inject

/**
 * Created by Dimorinny on 29.04.16.
 */
class EditSubscriptionActivity : PresentedActivity<EditSubscriptionPresenter>(), EditSubscriptionView {

    companion object {
        const val ARG_SOURCE_ID = "arg_source"
        const val ARG_SUBSCRIPTION_ID = "arg_subscription_id"
        const val ARG_SUBSCRIPTION_TITLE ="arg_subscription_title"
        const val ARG_SOURCE_COLOR = "arg_source_color"
    }

    @Inject
    lateinit var presenter: EditSubscriptionPresenter

    @Inject
    lateinit var iconUtils: IconUtils

    @Inject
    lateinit var colorUtils: ColorUtils

    lateinit var fragment: ParamsFragment
    lateinit var sourceId: String
    lateinit var subscriptionId: String
    lateinit var subscriptionTitle: String
    lateinit var subscription: String
    lateinit var sourceColor: String

    val unsubscribeProgressDialog: ProgressDialog by lazy { ProgressDialog(this) }
    val changeSubscriptionProgressDialog: ProgressDialog by lazy { ProgressDialog(this) }

    val subscriptionStateLayout: StateFrameLayout by bindView(R.id.subscription_state_layout)
    val errorPlaceholder: View by bindView(R.id.error_placeholder)
    val progressPlaceholder: View by bindView(R.id.progress_placeholder)
    val subscriptionContainer: ViewGroup by bindView(R.id.subscription_container)

    val subscriptionTitleView: TextView by bindView(R.id.subscription_title)
    val subscriptionSubtitleView: TextView by bindView(R.id.subscription_subtitle)
    val icon: ImageView by bindView(R.id.item_icon)
    val iconBackground: View by bindView(R.id.item_icon_background)
    val changeButton: Button by bindView(R.id.change_button)
    val unsubscribeButton: Button by bindView(R.id.unsubscribe_button)
    val toolbar: Toolbar by bindView(R.id.toolbar)

    val withSoundContainer: ViewGroup by bindView(R.id.subscribe_with_sound_container)
    val withSoundCheckbox: CheckBox by bindView(R.id.subscribe_with_sound_checkbox)
    val withAlertContainer: ViewGroup by bindView(R.id.subscribe_with_alert_container)
    val withAlertCheckbox: CheckBox by bindView(R.id.subscribe_with_alert_checkbox)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subscription)
        BaseApplication.graph.inject(this)

        initArgs()
        initStateLayout()
        initToolbar()
        initColors()
        initFragment()
        initPresenter(presenter)
        initViews()

        presenter.loadSourceAndSubscriptionFromCache(sourceId, subscriptionId)
        presenter.loadSourceAndSubscriptionFromNetwork(sourceId, subscriptionId)
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

    private fun initStateLayout() {
        subscriptionStateLayout.setNormalView(subscriptionContainer)
        subscriptionStateLayout.setErrorView(errorPlaceholder)
        subscriptionStateLayout.setProgressView(progressPlaceholder)
        subscriptionStateLayout.setState(State.STATE_PROGRESS)
    }

    private fun initViews() {
        title = subscriptionTitle
        unsubscribeProgressDialog.setMessage(resources.getString(R.string.subscription_unsubscribe_dialog_message))

        changeButton.setOnClickListener {
            presenter.changeSubscriptionButtonClicked(fragment.getValues())
        }

        unsubscribeButton.setOnClickListener {
            presenter.unsubscribeButtonClicked()
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
        subscriptionId = intent.extras.getString(ARG_SUBSCRIPTION_ID)
        subscriptionTitle = intent.extras.getString(ARG_SUBSCRIPTION_TITLE)
    }

    private fun initColors() {
        toolbar.setBackgroundColor(Color.parseColor(sourceColor))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = colorUtils.darker(Color.parseColor(sourceColor))
        }
    }

    override fun setSubscriptionData(subscription: PresentationSubscription) {
        subscriptionTitleView.text = subscription.title
        subscriptionSubtitleView.text = subscription.sourceTitle

        withSoundCheckbox.isChecked = subscription.sound
        withAlertCheckbox.isChecked = subscription.notification

        fragment.setValues(subscription.values)
        (iconBackground.background as GradientDrawable)
                .setColor(Color.parseColor(subscription.color))
        icon.setImageResource(iconUtils.getIcon(subscription.icon))
    }

    override fun setState(state: State) {
        subscriptionStateLayout.setState(state)
    }

    override fun validateFields(): Boolean {
        return fragment.validate()
    }

    override fun setParams(params: List<PresentationParam>) {
        fragment.setParams(params)
    }

    override fun initPresenter(presenter: EditSubscriptionPresenter) {
        presenter.view = this
        super.initPresenter(presenter)
    }

    override fun setTitle(sourceTitle: String) {
        title = sourceTitle
    }

    override fun showUnsubscribeProgressDialog() {
        unsubscribeProgressDialog.show()
    }

    override fun hideUnsubscribeProgressDialog() {
        unsubscribeProgressDialog.dismiss()
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun closeScreen() {
        finish()
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

        if (unsubscribeProgressDialog.isShowing) {
            unsubscribeProgressDialog.dismiss()
        }

        if (changeSubscriptionProgressDialog.isShowing) {
            changeSubscriptionProgressDialog.dismiss()
        }
    }
}