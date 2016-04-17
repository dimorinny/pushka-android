package ru.nbsp.pushka.presentation.device.feed.container.adapter

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import ru.nbsp.pushka.R
import ru.nbsp.pushka.presentation.core.model.device.PresentationDeviceType
import ru.nbsp.pushka.util.bindView
import java.util.*

/**
 * Created by Dimorinny on 07.04.16.
 */
class DeviceTypesAdapter(val context: Context) : RecyclerView.Adapter<DeviceTypesAdapter.ViewHolder>() {

    interface OnDeviceTypeItemClickListener {
        fun onItemClicked(deviceType: PresentationDeviceType)
    }

    var deviceTypeClickListener: OnDeviceTypeItemClickListener? = null

    var deviceTypes: List<PresentationDeviceType> = ArrayList()
        set(ses: List<PresentationDeviceType>) {
            field = ses
            notifyDataSetChanged()
        }


    open inner class ViewHolder(val holderView: View) : RecyclerView.ViewHolder(holderView) {
        val container: ViewGroup by bindView(R.id.item_create_device_container)
        val title: TextView by bindView(R.id.item_create_device_title)
        val icon: ImageView by bindView(R.id.item_icon)
        val iconBackground: View by bindView(R.id.item_icon_background)

        init {
            if (deviceTypeClickListener != null) {
                container.setOnClickListener {
                    deviceTypeClickListener?.onItemClicked(deviceTypes[adapterPosition])
                }
            }
        }
    }

    override fun onBindViewHolder(holder: DeviceTypesAdapter.ViewHolder, position: Int) {
        val device = deviceTypes[position]

        holder.title.text = device.title
        holder.icon.setImageResource(device.icon)
        (holder.iconBackground.background as GradientDrawable)
                .setColor(ContextCompat.getColor(context, device.color))
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): DeviceTypesAdapter.ViewHolder? {
        return ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_create_device, parent, false))
    }

    override fun getItemCount(): Int {
        return deviceTypes.size
    }
}