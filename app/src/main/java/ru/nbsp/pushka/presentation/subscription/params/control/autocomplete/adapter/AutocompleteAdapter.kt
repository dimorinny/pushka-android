package ru.nbsp.pushka.presentation.subscription.params.control.autocomplete.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import ru.nbsp.pushka.R
import ru.nbsp.pushka.presentation.core.model.subscription.PresentationListItem
import java.util.*

/**
 * Created by Dimorinny on 12.04.16.
 */
class AutoCompleteAdapter : BaseAdapter(), Filterable {

    var items: List<PresentationListItem> = ArrayList()
        set(items: List<PresentationListItem>) {
            field = items
            notifyDataSetChanged()
        }

    override fun getView(position: Int, savedView: View?, parent: ViewGroup): View? {
        var view = savedView

        if (view == null) {
            val inflater = LayoutInflater.from(parent.context)
            view = inflater.inflate(R.layout.item_simple_list, parent, false)
        }

        (view!!.findViewById(R.id.text) as TextView).text = items[position].name
        return view
    }

    override fun getItem(position: Int): Any? = items[position]
    override fun getItemId(position: Int): Long = position.toLong()
    override fun getCount(): Int = items.size

    override fun getFilter(): Filter? {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults? {
                return FilterResults()
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {}
        }
    }
}