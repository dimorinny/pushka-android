package ru.nbsp.pushka.presentation.subscription.params.control

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import ru.nbsp.pushka.R
import ru.nbsp.pushka.presentation.core.model.source.control.Option
import ru.nbsp.pushka.util.bindView

class DropdownControl(context: Context, val options: List<Option>, attrs: AttributeSet? = null) : LinearLayout(context, attrs), Control {

    val adapter: ArrayAdapter<Option> = ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, options)

    val title: TextView by bindView(R.id.dropdown_title)
    val errorIndicator: TextView by bindView(R.id.dropdown_error)
    val spinner: Spinner by bindView(R.id.dropdown_spinner)

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.control_dropdown, this, true)

        spinner.adapter = adapter
    }

    override fun setNoError() {
        errorIndicator.text = ""
    }

    override fun setError() {
        errorIndicator.text = "ERROR"
    }

    override fun getValue(): String? = options[spinner.selectedItemPosition].value

    override fun setOnChangeListener(onChangeListener: Control.OnChangeListener) {
        spinner.onItemSelectedListener = object :  AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                onChangeListener.onChange(options[position].value)
            }
        }
    }

    fun setTitle(value: String) {
        title.text = value
    }
}


