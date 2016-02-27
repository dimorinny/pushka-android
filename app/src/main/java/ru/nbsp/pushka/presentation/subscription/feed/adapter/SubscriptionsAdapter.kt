package ru.nbsp.pushka.presentation.subscription.feed.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import ru.nbsp.pushka.R
import ru.nbsp.pushka.network.model.subscription.Subscription
import ru.nbsp.pushka.presentation.core.adapter.OnItemClickListener
import ru.nbsp.pushka.util.bindView
import java.util.*

/**
 * Created by Dimorinny on 26.02.16.
 */
class SubscriptionsAdapter : RecyclerView.Adapter<SubscriptionsAdapter.ViewHolder>(){
    var itemClickListener: OnItemClickListener? = null

    var subscriptions: List<Subscription> = ArrayList()
        set(s : List<Subscription>) {
            field = s
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int {
        return subscriptions.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val subscription = subscriptions[position]

        holder.subscriptionTitle.text = subscription.title
        holder.subscriptionSubtitle.text = subscription.description
        holder.subscriptionImage.setImageResource(R.drawable.login_background)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_subscription, parent, false)
        return ViewHolder(view)
    }

    inner class ViewHolder(val holderView: View) : RecyclerView.ViewHolder(holderView) {
        val subscriptionContainer: ViewGroup by bindView(R.id.item_subscription_container)
        val subscriptionTitle: TextView by bindView(R.id.item_subscription_title)
        val subscriptionSubtitle: TextView by bindView(R.id.item_subscription_subtitle)
        val subscriptionImage: ImageView by bindView(R.id.item_subscription_image)

        init {
            if (itemClickListener != null) {
                subscriptionContainer.setOnClickListener {
                    itemClickListener?.onItemClicked(adapterPosition, subscriptionContainer)
                }
            }
        }
    }
}