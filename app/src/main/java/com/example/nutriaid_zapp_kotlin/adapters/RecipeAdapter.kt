package com.example.nutriaid_zapp_kotlin.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nutriaid_zapp_kotlin.R
import com.example.nutriaid_zapp_kotlin.RecipeActivity
import com.example.nutriaid_zapp_kotlin.models.dbResponse.DBRecipe

class RecipeAdapter : RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {

    var recipes = mutableListOf<DBRecipe>()

    fun setRecommendationList(recipes: List<DBRecipe>) {
        this.recipes = recipes.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.recipe_preview, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: DBRecipe = recipes[position]
        Glide.with(holder.itemView.context).load(item.image).into(holder.recipeImageview)
        holder.recipeTitle.text = item.title
        holder.recipeTime.text = item.readyInMinutes + " min"
        holder.recipeStar.text = item.aggregateLikes
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val context: Context = itemView.context

        var recipeListContainer: CardView = itemView.findViewById(R.id.recipe_preview_list_container)
        var recipeImageview: ImageView = itemView.findViewById(R.id.recipe_preview_imageView)
        var recipeTitle: TextView = itemView.findViewById(R.id.recipe_preview_title)
        var recipeTime: TextView = itemView.findViewById(R.id.recipe_preview_time)
        var recipeStar: TextView = itemView.findViewById(R.id.recipe_preview_star)

        init {
            recipeListContainer.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val intent: Intent = Intent(context, RecipeActivity::class.java)
            var recipeId:Int = recipes[adapterPosition].id

            intent.putExtra("recipeId", recipeId)
            context.startActivity(intent)
        }
    }
}
