package ru.nbsp.pushka.presentation.subscription.feed.adapter

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import ru.nbsp.pushka.R
import ru.nbsp.pushka.presentation.core.adapter.OnItemClickListener
import ru.nbsp.pushka.presentation.core.model.subscription.PresentationSubscription
import ru.nbsp.pushka.util.SourceIconUtils
import ru.nbsp.pushka.util.bindView
import java.util.*

/**
 * Created by Dimorinny on 26.02.16.
 */
class SubscriptionsAdapter(val sourceIconUtils: SourceIconUtils) : RecyclerView.Adapter<SubscriptionsAdapter.ViewHolder>() {
    var itemClickListener: OnItemClickListener? = null

    var subscriptions: List<PresentationSubscription> = ArrayList()
        set(s : List<PresentationSubscription>) {
            field = s
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int {
        return subscriptions.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val subscription = subscriptions[position]

        holder.title.text = subscription.title
        holder.subtitle.text = subscription.sourceTitle
        (holder.sourceIconBackground.background as GradientDrawable)
                .setColor(Color.parseColor(subscription.color))
        holder.sourceIcon.setImageResource(sourceIconUtils.getIcon(subscription.icon))
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_subscription, parent, false)
        return ViewHolder(view)
    }

    inner class ViewHolder(val holderView: View) : RecyclerView.ViewHolder(holderView) {
        val container: ViewGroup by bindView(R.id.item_subscription_container)
        val title: TextView by bindView(R.id.item_subscription_title)
        val subtitle: TextView by bindView(R.id.item_subscription_subtitle)
        val sourceIconBackground: View by bindView(R.id.item_icon_background)
        val sourceIcon: ImageView by bindView(R.id.item_icon)

        init {
            if (itemClickListener != null) {
                container.setOnClickListener {
                    itemClickListener?.onItemClicked(adapterPosition, container)
                }
            }
        }
    }
}