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
import com.example.nutriaid_zapp_kotlin.R
import com.example.nutriaid_zapp_kotlin.Recipe
import com.example.nutriaid_zapp_kotlin.RecipeActivity

class RecipeAdapter(private val data: List<Recipe>) : RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.recipe_preview, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: Recipe = data[position]
        holder.recipe_imageView.setImageResource(item.image)
        holder.recipe_title.text = item.title
        holder.recipe_time.text = item.readyInMinutes + " min"
        holder.recipe_star.text = item.aggregateLikes
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val context: Context = itemView.context

        var recipe_list_container: CardView = itemView.findViewById(R.id.recipe_preview_list_container)
        var recipe_imageView: ImageView = itemView.findViewById(R.id.recipe_preview_imageView)
        var recipe_title: TextView = itemView.findViewById(R.id.recipe_preview_title)
        var recipe_time: TextView = itemView.findViewById(R.id.recipe_preview_time)
        var recipe_star: TextView = itemView.findViewById(R.id.recipe_preview_star)

        init {
            recipe_list_container.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val intent: Intent = Intent(context, RecipeActivity::class.java)
            var recipeId:Int = data[adapterPosition].id

            intent.putExtra("recipeId", recipeId)
            context.startActivity(intent)
        }
    }
}
