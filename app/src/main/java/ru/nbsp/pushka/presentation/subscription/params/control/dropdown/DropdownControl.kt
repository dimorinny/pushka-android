package ru.nbsp.pushka.presentation.subscription.params.control.dropdown

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import ru.nbsp.pushka.R
import ru.nbsp.pushka.presentation.core.model.subscription.control.Option
import ru.nbsp.pushka.presentation.subscription.params.control.Control
import ru.nbsp.pushka.presentation.subscription.params.control.dropdown.adapter.DropdownAdapter
import ru.nbsp.pushka.util.bindView

class DropdownControl(context: Context, values: List<Option>, attrs: AttributeSet? = null)
        : LinearLayout(context, attrs), Control {

    val adapter: DropdownAdapter = DropdownAdapter(
            getContext(),
            android.R.layout.simple_spinner_dropdown_item,
            values,
            context.getString(R.string.subscribe_dropdown_placeholder))

    val title: TextView by bindView(R.id.dropdown_title)
    val errorIndicator: TextView by bindView(R.id.dropdown_error)
    val spinner: Spinner by bindView(R.id.dropdown_spinner)

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.control_dropdown, this, true)

        spinner.adapter = adapter
    }

    override fun setNoError() {
        errorIndicator.visibility = GONE
    }

    override fun setError() {
        errorIndicator.visibility = VISIBLE
        errorIndicator.text = "ERROR"
    }

    override fun getValue(): String? = adapter.options[spinner.selectedItemPosition].value

    override fun setValue(value: String?) {
        // Nothing to do here, because spinner save selected item position
    }

    override fun setOnChangeListener(onChangeListener: Control.OnChangeListener) {
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                onChangeListener.onChange(adapter.options[position].value)
            }
        }
    }

    fun setTitle(value: String) {
        title.text = value
    }
}


