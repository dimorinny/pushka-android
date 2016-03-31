package ru.nbsp.pushka.presentation.source.feed.adapter

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
import ru.nbsp.pushka.presentation.core.model.source.PresentationSource
import ru.nbsp.pushka.util.IconUtils
import ru.nbsp.pushka.util.bindView
import java.util.*

/**
 * Created by Dimorinny on 26.02.16.
 */
class SourcesAdapter(val iconUtils: IconUtils) : RecyclerView.Adapter<SourcesAdapter.SourceItemViewHolder>() {

    var itemClickListener: OnItemClickListener? = null

    var sources: List<PresentationSource> = ArrayList()
        set(s: List<PresentationSource>) {
            field = s
            notifyDataSetChanged()
        }

    override fun onBindViewHolder(holder: SourceItemViewHolder, position: Int) {
        val source = sources[position]

        holder.sourceTitle.text = source.name
        holder.sourceSubtitle.text = source.description

        (holder.sourceIconBackground.background as GradientDrawable)
                .setColor(Color.parseColor(source.color))
        holder.sourceIcon.setImageResource(iconUtils.getIcon(source.icon))
    }

    override fun getItemCount(): Int {
        return sources.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SourceItemViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_source, parent, false)
        return SourceItemViewHolder(view)
    }

    inner class SourceItemViewHolder(val holderView: View) : RecyclerView.ViewHolder(holderView) {
        val sourceContainer: ViewGroup by bindView(R.id.item_source_container)
        val sourceTitle: TextView by bindView(R.id.item_source_title)
        val sourceSubtitle: TextView by bindView(R.id.item_source_subtitle)
        val sourceIcon: ImageView by bindView(R.id.item_icon)
        val sourceIconBackground: View by bindView(R.id.item_icon_background)

        init {
            if (itemClickListener != null) {
                sourceContainer.setOnClickListener {
                    itemClickListener?.onItemClicked(adapterPosition, sourceContainer)
                }
            }
        }
    }
}