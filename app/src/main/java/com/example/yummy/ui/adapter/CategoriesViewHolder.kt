package com.example.yummy.ui.adapter

import android.view.View
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.yummy.R
import com.example.yummy.data.model.Category
import com.example.yummy.utlis.loadImage
import kotlinx.android.synthetic.main.item_category.view.*

class CategoriesViewHolder(itemView: View, onItemClicked: (Category) -> Unit) :
    RecyclerView.ViewHolder(itemView) {

    private var itemCategory: Category? = null

    init {
        itemView.setOnClickListener {
            itemCategory?.let { onItemClicked(it) }
            itemView.animation = AnimationUtils.loadAnimation(itemView.context, R.anim.anim_item)
        }
    }

    fun onBind(category: Category) {
        itemCategory = category
        itemView.apply {
            textCategoryName.text = category.name
            category.image?.let {
                imageCategory.loadImage(it)
            }
        }
    }
}
