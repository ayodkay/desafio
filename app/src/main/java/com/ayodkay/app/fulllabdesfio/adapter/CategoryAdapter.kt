package com.ayodkay.app.fulllabdesfio.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.*
import com.ayodkay.app.fulllabdesfio.R
import com.ayodkay.app.fulllabdesfio.activity.CategoryActivity
import com.ayodkay.app.fulllabdesfio.activity.SubCategories
import com.ayodkay.app.fulllabdesfio.database.Categories
import com.ayodkay.app.fulllabdesfio.model.CategoryModel
import org.json.JSONObject
import java.util.ArrayList

class CategoryAdapter internal constructor(private val context: Context):
    RecyclerView.Adapter<CategoryAdapter.CategoryModels>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    private var allCategory = emptyList<Categories>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryModels {
        val itemView = inflater.inflate(R.layout.category_list_item,parent,false)
        return CategoryModels(itemView)
    }

    override fun getItemCount(): Int {
        if (allCategory.isNullOrEmpty()){
            return 0
        }
        return allCategory.size
    }

    override fun onBindViewHolder(holder: CategoryModels, position: Int) {
        holder.itemName.text = allCategory[position].categoryName

        holder.itemName.setOnClickListener {
            context.startActivity(Intent(context,SubCategories::class.java).apply {

                putExtra("categoryName",allCategory[position].categoryName)
            })
        }
    }

    inner class CategoryModels(itemView: View): RecyclerView.ViewHolder(itemView){
        val itemName : TextView = itemView.findViewById(R.id.category_name)
    }


    internal fun setCategory(allCategory: List<Categories>){
        this.allCategory = allCategory
        notifyDataSetChanged()
    }
}