package ru.nbsp.pushka.presentation.subscription.params.control.autocomplete

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.AutoCompleteTextView
import android.widget.LinearLayout
import android.widget.TextView
import ru.nbsp.pushka.BaseApplication
import ru.nbsp.pushka.R
import ru.nbsp.pushka.presentation.core.model.subscription.PresentationListItem
import ru.nbsp.pushka.presentation.core.model.subscription.control.ListAttributes
import ru.nbsp.pushka.presentation.subscription.params.control.Control
import ru.nbsp.pushka.presentation.subscription.params.control.autocomplete.adapter.AutoCompleteAdapter
import ru.nbsp.pushka.util.bindView
import javax.inject.Inject

/**
 * Created by Dimorinny on 12.04.16.
 */
class AutoCompleteControl(context: Context, val attributes: ListAttributes, attrs: AttributeSet? = null)
        : LinearLayout(context, attrs), AutoCompleteControlView, Control {

    @Inject
    lateinit var presenter: AutoCompleteControlPresenter

    val adapter: AutoCompleteAdapter = AutoCompleteAdapter()
    val title: TextView by bindView(R.id.autocomplete_title)
    val errorIndicator: TextView by bindView(R.id.autocomplete_error)
    val text: AutoCompleteTextView by bindView(R.id.autocomplete)

    var currentValue: String? = null

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
        text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(value: CharSequence, start: Int, before: Int, count: Int) {
                presenter.loadItems(value.toString())
            }
        })
    }

    private fun initPresenter() {
        presenter.view = this
        presenter.attributes = attributes
        presenter.onCreate()
    }

    override fun setItems(items: List<PresentationListItem>) {
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
        presenter.onDestroy()
    }
}