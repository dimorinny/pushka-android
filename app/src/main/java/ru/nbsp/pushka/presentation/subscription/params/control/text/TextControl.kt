package ru.nbsp.pushka.presentation.subscription.params.control.text

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.jakewharton.rxbinding.widget.textChanges
import ru.nbsp.pushka.R
import ru.nbsp.pushka.presentation.subscription.params.control.Control
import ru.nbsp.pushka.presentation.subscription.params.control.autocomplete.AutoCompleteControl
import ru.nbsp.pushka.util.bindView
import rx.android.schedulers.AndroidSchedulers
import rx.subscriptions.CompositeSubscription
import java.util.concurrent.TimeUnit

/**
 * Created by Dimorinny on 25.04.16.
 */
class TextControl(context: Context, attrs: AttributeSet? = null)
    : LinearLayout(context, attrs), Control {

    val subscription: CompositeSubscription = CompositeSubscription()
    var listener: Control.OnChangeListener? = null

    val title: TextView by bindView(R.id.number_input_title)
    val error: TextView by bindView(R.id.number_input_error)
    val textInput: EditText by bindView(R.id.text_input)

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.control_text, this, true)
        initViews()
    }

    private fun initViews() {
        subscription.add(textInput.textChanges()
                .debounce(AutoCompleteControl.INPUT_DEBOUNCE, TimeUnit.MILLISECONDS)
                .map { it.toString() }
                .filter { !it.isEmpty() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe { listener?.onChange(it) })
    }

    override fun setNoError() {
        error.visibility = GONE
    }

    override fun setError() {
        error.visibility = VISIBLE
        error.text = "ERROR"
    }

    fun setTitle(value: String) {
        title.text = value
    }

    override fun getValue(): String? {
        return if (!textInput.text.isEmpty()) {
            textInput.text.toString()
        } else {
            null
        }
    }

    override fun setValue(value: String?) {
        if (value != null) {
            textInput.setText(value, TextView.BufferType.EDITABLE)
        }
    }

    override fun setOnChangeListener(onChangeListener: Control.OnChangeListener) {
        listener = onChangeListener
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        subscription.unsubscribe()
    }
}