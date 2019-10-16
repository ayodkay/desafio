package com.ayodkay.app.fulllabdesfio.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ayodkay.app.fulllabdesfio.R
import com.ayodkay.app.fulllabdesfio.database.Categories

class SubCategoryAdapter internal constructor(private val context: Context,val current_position: Int):
    RecyclerView.Adapter<SubCategoryAdapter.CategoryModels>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    private var allCategory = emptyList<Categories>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryModels {
        val itemView = inflater.inflate(R.layout.sub_categories_item,parent,false)
        return CategoryModels(itemView)
    }

    override fun getItemCount(): Int {
        if (allCategory.isNullOrEmpty()){
            return 0
        }
        return allCategory[current_position].subCategoryName.size
    }

    override fun onBindViewHolder(holder: CategoryModels, position: Int) {
      val  x=  allCategory[current_position].subCategoryName

       holder.itemName.text = x[position]
    }

    inner class CategoryModels(itemView: View): RecyclerView.ViewHolder(itemView){
        val itemName : TextView = itemView.findViewById(R.id.sub_category_name)
    }

    internal fun setCategory(allCategory: List<Categories>){
        this.allCategory = allCategory
        notifyDataSetChanged()
    }
}