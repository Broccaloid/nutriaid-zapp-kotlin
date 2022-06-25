package com.example.nutriaid_zapp_kotlin.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nutriaid_zapp_kotlin.databinding.ShortRecipeListItemBinding
import com.example.nutriaid_zapp_kotlin.models.shortRecipe.ShortRecipe

class ShortRecipeListAdapter: RecyclerView.Adapter<MainViewHolder>() {
    var recipes = mutableListOf<ShortRecipe>()
    fun setRecipeList(recipes: List<ShortRecipe>) {
        this.recipes = recipes.toMutableList()
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ShortRecipeListItemBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.binding.name.text = recipe.title
        Glide.with(holder.itemView.context).load(recipe.image).into(holder.binding.imageview)
    }
    override fun getItemCount(): Int {
        return recipes.size
    }
}
class MainViewHolder(val binding: ShortRecipeListItemBinding) : RecyclerView.ViewHolder(binding.root) {
}
