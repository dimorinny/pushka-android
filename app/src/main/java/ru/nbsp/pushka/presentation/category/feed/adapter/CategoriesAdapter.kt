package ru.nbsp.pushka.presentation.category.feed.adapter

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import ru.nbsp.pushka.R
import ru.nbsp.pushka.presentation.core.adapter.OnItemClickListener
import ru.nbsp.pushka.presentation.core.model.source.PresentationCategory
import ru.nbsp.pushka.util.ColorUtils
import ru.nbsp.pushka.util.IconUtils
import ru.nbsp.pushka.util.bindView
import java.util.*

/**
 * Created by Dimorinny on 08.03.16.
 */
class CategoriesAdapter(val iconUtils: IconUtils, val colorUtils: ColorUtils) : RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {

    var itemClickListener: OnItemClickListener? = null

    var categories: List<PresentationCategory> = ArrayList()
        set(s : List<PresentationCategory>) {
            field = s
            notifyDataSetChanged()
        }

    inner class CategoryViewHolder(holderView: View) : RecyclerView.ViewHolder(holderView) {
        val container: ViewGroup by bindView(R.id.item_category_container)
        val icon: ImageView by bindView(R.id.item_category_icon)
        val title: TextView by bindView(R.id.item_category_title)
        val imageContainer: ViewGroup by bindView(R.id.item_category_image_container)
        val titleContainer: ViewGroup by bindView(R.id.item_category_title_container)

        init {
            if (itemClickListener != null) {
                container.setOnClickListener {
                    itemClickListener?.onItemClicked(adapterPosition, container)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]

        holder.title.text = category.name
        holder.imageContainer.setBackgroundColor(Color.parseColor(category.color))
        holder.titleContainer.setBackgroundColor(colorUtils.darker(Color.parseColor(category.color)))
        holder.icon.setImageResource(iconUtils.getIcon(category.icon))
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CategoryViewHolder? {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return categories.size
    }
}