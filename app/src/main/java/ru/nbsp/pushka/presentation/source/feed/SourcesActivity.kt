package ru.nbsp.pushka.presentation.source.feed

import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.transition.Transition
import android.transition.TransitionInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso
import ru.nbsp.pushka.BaseApplication
import ru.nbsp.pushka.R
import ru.nbsp.pushka.presentation.core.base.BaseActivity
import ru.nbsp.pushka.presentation.core.model.source.PresentationCategory
import ru.nbsp.pushka.util.GUIUtils
import ru.nbsp.pushka.util.IntentUtils
import ru.nbsp.pushka.util.bindView
import javax.inject.Inject

/**
 * Created by Dimorinny on 08.03.16.
 */
class SourcesActivity : BaseActivity() {

    companion object {
        const val ARG_CATEGORY = "arg_category"
        const val STATE_CATEGORY = "state_category"

        private const val ENTER_ANIMATION_DURATION = 300L
        private const val EXPAND_CIRCLE_ANIMATION_DURATION = 600L
        private const val SHOW_COLLAPSING_TOOLBAR_ANIMATION_DURATION = 100L
    }

    lateinit var fragment: Fragment
    lateinit var category: PresentationCategory

    val toolbar: Toolbar by bindView(R.id.toolbar)
    val toolbarImage: ImageView by bindView(R.id.toolbar_image)
    val collapsingToolbar: CollapsingToolbarLayout by bindView(R.id.collapsing_toolbar)
    val sharedCircle: View by bindView(R.id.shared_animation_circle)
    val overlay: View by bindView(R.id.overlay)

    @Inject
    lateinit var intentUtils: IntentUtils

    @Inject
    lateinit var picasso: Picasso

    val guiUtils = GUIUtils()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sources)
        BaseApplication.graph.inject(this)

        initToolbar()
        initArguments()
        initState(savedInstanceState)

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP || savedInstanceState != null) {
            initFragment()
            setViewData()
            disableSharedTransitionState(false)
        } else {
            setupEnterAnimation()
        }
    }

    private fun initStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    private fun initFragment() {
        val fragmentManager = supportFragmentManager

        var cachedFragment: Fragment? = fragmentManager.findFragmentById(R.id.main_container)

        if (cachedFragment == null) {
            cachedFragment = SourcesFragment()
            cachedFragment.arguments = intentUtils.intentToFragmentArguments(cachedFragment.arguments, intent)

            fragmentManager.beginTransaction().replace(R.id.main_container, cachedFragment).commitAllowingStateLoss()
        }

        fragment = cachedFragment
    }

    private fun initArguments() {
        if (intent.getSerializableExtra(ARG_CATEGORY) != null) {
            category = intent.extras.getSerializable(ARG_CATEGORY) as PresentationCategory
        }
    }

    private fun initState(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            if (savedInstanceState.getSerializable(STATE_CATEGORY) != null) {
                category = savedInstanceState.getSerializable(STATE_CATEGORY) as PresentationCategory
            }
        }
    }

    private fun setViewData() {
        collapsingToolbar.title = category.name
        picasso.load(category.image)
                .fit()
                .centerCrop()
                .into(toolbarImage)
    }

    private fun disableSharedTransitionState(animated: Boolean) {
        initStatusBar()
        overlay.visibility = View.GONE
        if (animated) {
            val fadeOut = ObjectAnimator.ofFloat(sharedCircle, "alpha", 1f, 0f)
            fadeOut.duration = SHOW_COLLAPSING_TOOLBAR_ANIMATION_DURATION
            fadeOut.start()
        } else {
            sharedCircle.visibility = View.GONE
        }
    }

    private fun expandSharedCircleView() {
        sharedCircle.setBackgroundResource(0)
        sharedCircle.layoutParams = CollapsingToolbarLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
    }

    private fun setupEnterAnimation() {
        val transition = TransitionInflater.from(this).inflateTransition(R.transition.changebounds_with_arcmotion)
        transition.duration = ENTER_ANIMATION_DURATION
        window.sharedElementEnterTransition = transition

        transition.addListener(object : Transition.TransitionListener {
            override fun onTransitionEnd(p0: Transition?) {
                initFragment()
                setViewData()
                startRevealAnimation()
            }

            override fun onTransitionResume(p0: Transition?) {}
            override fun onTransitionPause(p0: Transition?) {}
            override fun onTransitionCancel(p0: Transition?) {}
            override fun onTransitionStart(p0: Transition?) {}
        });
    }

    private fun startRevealAnimation() {
        val cx = (toolbarImage.left + toolbarImage.right) / 2
        val cy = (toolbarImage.top + toolbarImage.bottom - resources.getDimension(R.dimen.status_bar_height).toInt()) / 2

        val startRadius = sharedCircle.width / 2
        val finalRadius = Math.hypot(toolbarImage.width.toDouble(), toolbarImage.height.toDouble()).toFloat()

        guiUtils.animateRevealShow(this, sharedCircle, EXPAND_CIRCLE_ANIMATION_DURATION, startRadius,
                finalRadius, R.color.colorPrimary, cx, cy, object : GUIUtils.OnRevealAnimationListener {

            override fun onRevealStart() {
                expandSharedCircleView()
            }

            override fun onRevealStop() {
                disableSharedTransitionState(true)
            }
        })
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

    override fun onBackPressed() {
        finish()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            (supportActionBar as ActionBar).setDisplayHomeAsUpEnabled(true);
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable(STATE_CATEGORY, category)
        super.onSaveInstanceState(outState)
    }
}