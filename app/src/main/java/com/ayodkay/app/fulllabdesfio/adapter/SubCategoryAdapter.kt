package com.ayodkay.app.fulllabdesfio.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ayodkay.app.fulllabdesfio.R
import com.ayodkay.app.fulllabdesfio.database.Categories

class SubCategoryAdapter internal constructor(private val context: Context):
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
        return allCategory.size
    }

    override fun onBindViewHolder(holder: CategoryModels, position: Int) {
         allCategory[position].subCategoryName.forEach {
             holder.itemName.text = it
         }
    }

    inner class CategoryModels(itemView: View): RecyclerView.ViewHolder(itemView){
        val itemName : TextView = itemView.findViewById(R.id.sub_category_name)
    }

    internal fun setCategory(allCategory: List<Categories>){
        this.allCategory = allCategory
        notifyDataSetChanged()
    }
}