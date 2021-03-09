package com.example.yummy.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.yummy.R
import com.example.yummy.data.model.Category
import com.example.yummy.data.model.Ingredient
import com.example.yummy.utlis.loadImage
import kotlinx.android.synthetic.main.item_category.view.*
import kotlinx.android.synthetic.main.item_ingredient.view.*

class IngredientsViewHolder(itemView: View, onItemClicked: (Ingredient) -> Unit) :
    RecyclerView.ViewHolder(itemView) {

    private var itemIngredient: Ingredient? = null

    init {
        itemView.setOnClickListener {
            itemIngredient?.let { onItemClicked(it) }
        }
    }

    fun onBind(ingredient: Ingredient) {
        itemIngredient = ingredient
        itemView.apply {
            textIngredient.text = ingredient.name
            textContent.text =
                if (ingredient.description != "null") ingredient.description
                else resources.getString(R.string.text_update)
        }
    }
}
