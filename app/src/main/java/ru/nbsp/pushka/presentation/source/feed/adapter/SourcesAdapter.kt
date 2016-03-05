package ru.nbsp.pushka.presentation.source.feed.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.afollestad.sectionedrecyclerview.SectionedRecyclerViewAdapter
import ru.nbsp.pushka.R
import ru.nbsp.pushka.data.model.source.Source
import ru.nbsp.pushka.presentation.core.adapter.OnItemClickListener
import ru.nbsp.pushka.util.bindView
import java.util.*

/**
 * Created by Dimorinny on 26.02.16.
 */
class SourcesAdapter : SectionedRecyclerViewAdapter<SourcesAdapter.SourceItemViewHolder>() {

    var itemClickListener: OnItemClickListener? = null

    var sources: List<Source> = ArrayList()
        set(s : List<Source>) {
            field = s
            notifyDataSetChanged()
        }

    override fun getSectionCount(): Int {
        return 1;
    }

    override fun getItemCount(section: Int): Int {
        return sources.size
    }

    override fun onBindHeaderViewHolder(holder: SourceItemViewHolder, section: Int) {
        // Setup header view.
    }

    override fun onBindViewHolder(holder: SourceItemViewHolder, section: Int, relativePosition: Int, absolutePosition: Int) {
        val source = sources[absolutePosition]

        holder.sourceTitle.text = source.name
        holder.sourceSubtitle.text = source.description
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SourceItemViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_source, parent, false)
        return SourceItemViewHolder(view)
    }

    inner class SourceItemViewHolder(val holderView: View) : RecyclerView.ViewHolder(holderView) {
        val sourceContainer: ViewGroup by bindView(R.id.item_source_container)
        val sourceTitle: TextView by bindView(R.id.item_source_title)
        val sourceSubtitle: TextView by bindView(R.id.item_source_subtitle)
        val sourceImage: ImageView by bindView(R.id.item_source_image)

        init {
            if (itemClickListener != null) {
                sourceContainer.setOnClickListener {
                    itemClickListener?.onItemClicked(adapterPosition, sourceContainer)
                }
            }
        }
    }
}