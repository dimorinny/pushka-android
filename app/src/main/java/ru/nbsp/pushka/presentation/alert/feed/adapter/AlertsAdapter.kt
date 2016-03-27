package ru.nbsp.pushka.presentation.alert.feed.adapter

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
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
import ru.nbsp.pushka.util.IconUtils
import ru.nbsp.pushka.util.bindView
import java.util.*

/**
 * Created by Dimorinny on 24.02.16.
 */
class AlertsAdapter(val picasso: Picasso, val iconUtils: IconUtils)
        : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_IMAGE = 0
        private const val TYPE_PLAIN = 1
    }

    var itemClickListener: OnItemClickListener? = null

    var alerts: List<PresentationAlert> = ArrayList()
        set(s : List<PresentationAlert>) {
            field = s

            notifyDataSetChanged()
        }

    inner class ImageItem(holderView: View) : PlainItem(holderView) {
        val alertImageContainer: ViewGroup by bindView(R.id.item_alert_image_container)
        val alertImage: ImageView by bindView(R.id.item_alert_image)
    }

    open inner class PlainItem(val holderView: View) : RecyclerView.ViewHolder(holderView) {
        val alertContainer: ViewGroup by bindView(R.id.item_alert_container)
        val alertTitle: TextView by bindView(R.id.item_alert_title)
        val alertText: TextView by bindView(R.id.item_alert_text)
        val alertSourceIconBackground: View by bindView(R.id.item_source_icon_background)
        val sourceIcon: ImageView by bindView(R.id.item_source_icon_icon)
        val sourceTitle: TextView by bindView(R.id.item_alert_source_title)
        val sourceDate: TextView by bindView(R.id.item_alert_date)

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
        (holderItem.alertSourceIconBackground.background as GradientDrawable)
                .setColor(Color.parseColor(alert.color))
        holderItem.sourceTitle.text = alert.sourceTitle
        holderItem.sourceIcon.setImageResource(iconUtils.getIcon(alert.sourceImage))

        if (holder is ImageItem) {
            picasso
                    .load(alert.photo)
                    .fit()
                    .centerCrop()
                    .into(holder.alertImage)
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