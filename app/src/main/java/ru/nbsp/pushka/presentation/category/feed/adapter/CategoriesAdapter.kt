package ru.nbsp.pushka.presentation.category.feed.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import ru.nbsp.pushka.R
import ru.nbsp.pushka.presentation.core.adapter.OnItemClickListener
import ru.nbsp.pushka.presentation.core.model.source.PresentationCategory
import ru.nbsp.pushka.util.bindView
import java.util.*

/**
 * Created by Dimorinny on 08.03.16.
 */
class CategoriesAdapter(val picasso: Picasso) : RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {

    var itemClickListener: OnItemClickListener? = null

    var categories: List<PresentationCategory> = ArrayList()
        set(s : List<PresentationCategory>) {
            field = s
            notifyDataSetChanged()
        }

    inner class CategoryViewHolder(val holderView: View) : RecyclerView.ViewHolder(holderView) {
        val categoryContainer: ViewGroup by bindView(R.id.item_category_container)
        val categoryImage: ImageView by bindView(R.id.item_category_image)
        val categoryTitle: TextView by bindView(R.id.item_category_title)

        init {
            if (itemClickListener != null) {
                categoryContainer.setOnClickListener {
                    itemClickListener?.onItemClicked(adapterPosition, categoryContainer)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]

        holder.categoryTitle.text = category.name
        picasso.load(category.image)
                .fit()
                .centerCrop()
                .into(holder.categoryImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CategoryViewHolder? {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return categories.size
    }
}