package ru.nbsp.pushka.presentation.alert.feed.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import ru.nbsp.pushka.R
import ru.nbsp.pushka.presentation.core.adapter.OnItemClickListener
import ru.nbsp.pushka.util.bindView

/**
 * Created by Dimorinny on 24.02.16.
 */
class AlertsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_IMAGE = 0
        private const val TYPE_PLAIN = 0
    }

    var itemClickListener: OnItemClickListener? = null

    inner class ImageItem(holderView: View) : PlainItem(holderView) {
        val alertImage: ImageView by bindView(R.id.item_alert_image)
    }

    open inner class PlainItem(var holderView: View) : RecyclerView.ViewHolder(holderView) {
        val alertContainer: ImageView by bindView(R.id.item_alert_container)
        val alertTitle: TextView by bindView(R.id.item_alert_tite)
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
        // TODO: instance of here
//        val server = servers[position]
//        val image = ImageUtils.imageByMadeFrom(server.madeFrom)
//
//        holder.image.setImageDrawable(DrawableUtils.getDrawable(context, image))
//        holder.hostName.text = server.hostName
//        holder.serverName.text = server.name
//        holder.location.text = server.locations
//        holder.status.text = context.getString(StatusUtils.getStatus(server.status))
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? {
        return when (viewType) {
            TYPE_IMAGE -> {
                val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_alert_image, parent, false)
                PlainItem(view)
            }
            TYPE_PLAIN -> {
                val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_alert_plain, parent, false)
                ImageItem(view)
            }
            else -> null
        }
    }

    override fun getItemCount(): Int {
        return 0
    }
}