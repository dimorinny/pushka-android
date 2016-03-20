package ru.nbsp.pushka.presentation.alert.feed.adapter

import android.app.LauncherActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import ru.nbsp.pushka.R
import ru.nbsp.pushka.presentation.core.adapter.OnItemClickListener
import ru.nbsp.pushka.presentation.core.model.alert.PresentationAlert
import ru.nbsp.pushka.presentation.core.model.source.control.SimpleListControlItem
import ru.nbsp.pushka.util.bindView
import java.util.*

/**
 * Created by Dimorinny on 24.02.16.
 */
class SimpleListAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var itemClickListener: OnItemClickListener? = null

    var items: List<SimpleListControlItem> = ArrayList()
        set(s : List<SimpleListControlItem>) {
            field = s
            notifyDataSetChanged()
        }

    open inner class Item(val holderView: View) : RecyclerView.ViewHolder(holderView) {
        val title: TextView by bindView(R.id.simple_list_control_item_title)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val holderItem = holder as Item
        val item = items[position]

        holderItem.title.text = item.title
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? {
        return Item(LayoutInflater.from(parent?.context).inflate(R.layout.item_simple_list_control_item,
                parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }
}