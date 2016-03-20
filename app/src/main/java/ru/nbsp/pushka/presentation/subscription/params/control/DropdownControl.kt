package ru.nbsp.pushka.presentation.subscription.params.control

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView

import java.util.ArrayList

import ru.nbsp.pushka.R

class DropdownControl

@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : LinearLayout(context, attrs), Control {
    override fun setNoError() {
        errorIndicator.text = ""
    }

    override fun setError() {
        errorIndicator.text = "ERROR"
        Log.d("hey", "set textview to error")
    }

    internal class Option(var name: String, var value: String) {

        override fun toString(): String {
            return name
        }
    }

    private val titleView: TextView
    private val errorIndicator: TextView
    private val spinner: Spinner

    private val list: ArrayList<Option>
    private val adapter: ArrayAdapter<Option>

    init {

        orientation = LinearLayout.HORIZONTAL
        setGravity(Gravity.CENTER_VERTICAL)

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.control_dropdown, this, true)

        titleView = findViewById(R.id.titleView) as TextView
        errorIndicator = findViewById(R.id.errorIndicator) as TextView
        spinner = findViewById(R.id.spinner) as Spinner

        list = ArrayList<Option>()
        adapter = ArrayAdapter<Option>(getContext(), android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        addOption("Ничего не выбрано", "null")
    }

    fun addOption(name: String, value: String) {
        adapter.add(Option(name, value))
        adapter.notifyDataSetChanged()
    }

    fun setTitle(title: String) {
        titleView.text = title
    }

    override fun getValue(): String? {
        var value = adapter.getItem(spinner.selectedItemPosition).value
        if (value.equals("null")) {
            return null
        } else {
            return value
        }
    }

    override fun setOnChangeCallback(callback: Control.Callback) {
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                callback.onChange(adapter.getItem(position).value)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                throw NotImplementedError()
            }
        }
    }
}


