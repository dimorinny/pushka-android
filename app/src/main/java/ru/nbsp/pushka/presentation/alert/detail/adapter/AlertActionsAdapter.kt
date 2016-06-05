package ru.nbsp.pushka.presentation.alert.detail.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import ru.nbsp.pushka.R
import ru.nbsp.pushka.presentation.core.adapter.OnItemClickListener
import ru.nbsp.pushka.presentation.core.model.alert.PresentationAction
import ru.nbsp.pushka.util.bindView
import java.util.*

/**
 * Created by Dimorinny on 17.03.16.
 */
class AlertActionsAdapter : RecyclerView.Adapter<AlertActionsAdapter.ViewHolder>() {

    var itemClickListener: OnItemClickListener? = null

    var actions: List<PresentationAction> = ArrayList()
        set(s : List<PresentationAction>) {
            field = s
            notifyDataSetChanged()
        }


    open inner class ViewHolder(val holderView: View) : RecyclerView.ViewHolder(holderView) {
        val actionContainer: ViewGroup by bindView(R.id.item_alert_action_container)
        val actionTitle: TextView by bindView(R.id.item_alert_action_title)
        val actionImage: ImageView by bindView(R.id.item_alert_action_image)

        init {
            if (itemClickListener != null) {
                actionContainer.setOnClickListener {
                    itemClickListener?.onItemClicked(adapterPosition, actionContainer)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: AlertActionsAdapter.ViewHolder, position: Int) {
        // TODO: add action info
        val action = actions[position]
        holder.actionTitle.text = "Открыть урл"
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): AlertActionsAdapter.ViewHolder? {
        return ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_alert_action, parent, false))
    }

    override fun getItemCount(): Int {
        return actions.size
    }
}