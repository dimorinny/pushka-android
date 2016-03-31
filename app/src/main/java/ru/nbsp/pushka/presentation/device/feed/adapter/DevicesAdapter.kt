package ru.nbsp.pushka.presentation.device.feed.adapter

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
import ru.nbsp.pushka.presentation.core.model.device.PresentationDevice
import ru.nbsp.pushka.util.DeviceTokenUtils
import ru.nbsp.pushka.util.bindView
import java.util.*

/**
 * Created by Dimorinny on 31.03.16.
 */
class DevicesAdapter(val deviceTokenUtils: DeviceTokenUtils) : RecyclerView.Adapter<DevicesAdapter.ViewHolder>() {
    var itemClickListener: OnItemClickListener? = null

    var devices: List<PresentationDevice> = ArrayList()
        set(s : List<PresentationDevice>) {
            field = s
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int {
        return devices.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val device = devices[position]
        val deviceInfo = deviceTokenUtils.getDeviceNameById(device.type)

        holder.deviceTitle.text = device.name
        holder.deviceSubtitle.text = deviceInfo.name

        // TODO: hardcode
        (holder.deviceIconBackground.background as GradientDrawable).setColor(Color.parseColor(deviceInfo.color))
        holder.deviceIcon.setImageResource(deviceInfo.icon)

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_device, parent, false)
        return ViewHolder(view)
    }

    inner class ViewHolder(val holderView: View) : RecyclerView.ViewHolder(holderView) {
        val deviceContainer: ViewGroup by bindView(R.id.item_device_container)
        val deviceTitle: TextView by bindView(R.id.item_device_title)
        val deviceSubtitle: TextView by bindView(R.id.item_device_subtitle)
        val deviceIcon: ImageView by bindView(R.id.item_icon)
        val deviceIconBackground: View by bindView(R.id.item_icon_background)

        init {
            if (itemClickListener != null) {
                deviceContainer.setOnClickListener {
                    itemClickListener?.onItemClicked(adapterPosition, deviceContainer)
                }
            }
        }
    }
}