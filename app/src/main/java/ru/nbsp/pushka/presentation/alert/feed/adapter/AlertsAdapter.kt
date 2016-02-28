package ru.nbsp.pushka.presentation.alert.feed.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import ru.nbsp.pushka.R
import ru.nbsp.pushka.network.model.alert.Alert
import ru.nbsp.pushka.presentation.core.adapter.OnItemClickListener
import ru.nbsp.pushka.util.bindView
import java.util.*

/**
 * Created by Dimorinny on 24.02.16.
 */
class AlertsAdapter(val picasso: Picasso) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_IMAGE = 0
        private const val TYPE_PLAIN = 1
    }

    var itemClickListener: OnItemClickListener? = null

    var alerts: List<Alert> = ArrayList()
        set(s : List<Alert>) {
            field = s
            notifyDataSetChanged()
        }

    inner class ImageItem(holderView: View) : PlainItem(holderView) {
        val alertImage: ImageView by bindView(R.id.item_alert_image)
    }

    open inner class PlainItem(val holderView: View) : RecyclerView.ViewHolder(holderView) {
        val alertContainer: ViewGroup by bindView(R.id.item_alert_container)
        val alertTitle: TextView by bindView(R.id.item_alert_title)
        val alertText: TextView by bindView(R.id.item_alert_text)
        val sourceTitle: TextView by bindView(R.id.item_alert_source_title)
        val sourceImage: ImageView by bindView(R.id.item_alert_source_image)
        val sourceShare: ImageView by bindView(R.id.item_alert_share)

        init {
            if (itemClickListener != null) {
                alertContainer.setOnClickListener {
                    itemClickListener?.onItemClicked(adapterPosition, alertContainer)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val holderItem = holder as PlainItem
        val alert = alerts[position]

        holderItem.alertTitle.text = alert.title
        holderItem.alertText.text = alert.text
//        holderItem.sourceTitle.text = alert.sourceTitle

//        picasso.load(alert.sourceImage).into(holderItem.sourceImage)

        if (holder is ImageItem) {
            picasso.load(alert.photo).into(holder.alertImage)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (alerts[position].photo == null) {
            TYPE_PLAIN
        } else {
            TYPE_IMAGE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? {
        return when (viewType) {
            TYPE_IMAGE -> {
                val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_alert_image, parent, false)
                ImageItem(view)
            }
            TYPE_PLAIN -> {
                val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_alert_plain, parent, false)
                PlainItem(view)
            }
            else -> null
        }
    }

    override fun getItemCount(): Int {
        return alerts.size
    }
}