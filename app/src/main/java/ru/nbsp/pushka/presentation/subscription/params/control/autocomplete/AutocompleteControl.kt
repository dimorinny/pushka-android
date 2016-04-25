package ru.nbsp.pushka.presentation.subscription.params.control.autocomplete

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.jakewharton.rxbinding.widget.textChanges
import ru.nbsp.pushka.BaseApplication
import ru.nbsp.pushka.R
import ru.nbsp.pushka.presentation.core.model.subscription.PresentationListItem
import ru.nbsp.pushka.presentation.core.model.subscription.control.ListAttributes
import ru.nbsp.pushka.presentation.subscription.params.control.Control
import ru.nbsp.pushka.presentation.subscription.params.control.autocomplete.adapter.AutoCompleteAdapter
import ru.nbsp.pushka.util.bindView
import rx.android.schedulers.AndroidSchedulers
import rx.subscriptions.CompositeSubscription
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by Dimorinny on 12.04.16.
 */
class AutoCompleteControl(context: Context, val attributes: ListAttributes, attrs: AttributeSet? = null)
        : LinearLayout(context, attrs), AutoCompleteControlView, Control {

    companion object {
        const val INPUT_DEBOUNCE = 200L
    }

    @Inject
    lateinit var presenter: AutoCompleteControlPresenter

    val adapter: AutoCompleteAdapter = AutoCompleteAdapter()
    val title: TextView by bindView(R.id.autocomplete_title)
    val errorIndicator: TextView by bindView(R.id.autocomplete_error)
    val text: AutoCompleteTextView by bindView(R.id.autocomplete)
    val progress: ProgressBar by bindView(R.id.autocomplete_progress)

    var currentValue: String? = null

    val subscription = CompositeSubscription()

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.control_autocomplete, this, true)
        BaseApplication.graph.inject(this)

        initPresenter()
        initViews()
    }

    private fun initViews() {
        text.clearFocus()
        text.setAdapter(adapter)
        subscription.add(text.textChanges()
                .debounce(INPUT_DEBOUNCE, TimeUnit.MILLISECONDS)
                .map { it.toString() }
                .filter { !it.isEmpty() }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { progress.visibility = View.VISIBLE }
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe { presenter.loadItems(it) })
    }

    private fun initPresenter() {
        presenter.view = this
        presenter.attributes = attributes
        presenter.onCreate()
    }

    override fun setItems(items: List<PresentationListItem>) {
        progress.visibility = View.INVISIBLE
        adapter.items = items
    }

    override fun setNoError() {
        errorIndicator.visibility = GONE
    }

    override fun setError() {
        errorIndicator.visibility = VISIBLE
        errorIndicator.text = "ERROR"
    }

    override fun getValue(): String? = currentValue

    override fun setOnChangeListener(onChangeListener: Control.OnChangeListener) {
        text.setOnItemClickListener { adapterView, view, i, l ->
            currentValue = adapter.items[i].value
            onChangeListener.onChange(currentValue)
        }
    }

    fun setTitle(value: String) {
        title.text = value
    }

    override fun setValue(value: String?) {
        currentValue = value
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        subscription.unsubscribe()
        presenter.onDestroy()
    }
}