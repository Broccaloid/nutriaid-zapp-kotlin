package com.example.nutriaid_zapp_kotlin.adapters

import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.nutriaid_zapp_kotlin.R
import com.example.nutriaid_zapp_kotlin.models.dbResponse.DBIngredient

class ShoppingListAdapter(private val data: List<DBIngredient>) : RecyclerView.Adapter<ShoppingListAdapter.ViewHolder>() {
    private val itemStateArray = SparseBooleanArray()
    var selectedItems = mutableListOf<Long>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.shopping_list_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: DBIngredient = data[position]
        holder.shoppingListItem.text = "${item.amount} ${item.unit}: ${item.name}"
        holder.shoppingListItem.isChecked = itemStateArray.get(position, false)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var shoppingListItem: CheckBox = itemView.findViewById(R.id.shopping_list_item)

        init {
            shoppingListItem.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            val shoppingListItemState: Boolean = shoppingListItem.isChecked
            itemStateArray.put(layoutPosition, shoppingListItemState)
            if(shoppingListItemState) {
                selectedItems.add(data[adapterPosition].ingredientId)
            } else {
                selectedItems.remove(data[adapterPosition].ingredientId)
            }
        }
    }
}