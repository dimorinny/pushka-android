package ru.nbsp.pushka.presentation.subscription.params.control.dropdown.adapter

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import ru.nbsp.pushka.presentation.core.model.source.control.Option

/**
 * Created by Dimorinny on 09.04.16.
 */
class DropdownAdapter(context: Context, layout: Int, items: List<Option>, placeholder: String)
        : ArrayAdapter<Option>(context, layout, listOf(Option(placeholder, null)) + items) {

    val options: List<Option>

    init {
        options = listOf(Option(placeholder, null)) + items
    }

    override fun isEnabled(position: Int): Boolean {
        return position != 0
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val view = super.getDropDownView(position, convertView, parent) as TextView
        view.setTextColor(if (position == 0) Color.GRAY else Color.BLACK)
        return view
    }
}