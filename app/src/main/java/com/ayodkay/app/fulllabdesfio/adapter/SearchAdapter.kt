package com.ayodkay.app.fulllabdesfio.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.*
import com.ayodkay.app.fulllabdesfio.R
import com.ayodkay.app.fulllabdesfio.activity.SubCategories
import com.ayodkay.app.fulllabdesfio.model.CategoryModel
import com.ayodkay.app.fulllabdesfio.model.SearchModel
import org.json.JSONObject
import java.util.ArrayList

class SearchAdapter internal constructor(private val context: Context, private val allCategory: ArrayList<SearchModel>):
    RecyclerView.Adapter<SearchAdapter.CategoryModels>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryModels {
        val itemView = inflater.inflate(R.layout.list_item,parent,false)
        return CategoryModels(itemView)
    }

    override fun getItemCount(): Int {
        if (allCategory.isNullOrEmpty()){
            return 0
        }
        return allCategory.size
    }

    override fun onBindViewHolder(holder: CategoryModels, position: Int) {
        holder.itemName.text = allCategory[position].productName
        holder.itemIcon.setImageBitmap(allCategory[position].productImage)
        holder.itemPrice.text = allCategory[position].productPrice
        holder.itemPaymentOption.text = allCategory[position].productPaymentOption
    }

    inner class CategoryModels(itemView: View): RecyclerView.ViewHolder(itemView){
        val itemIcon : ImageView = itemView.findViewById(R.id.item_image)
        val itemName : TextView = itemView.findViewById(R.id.item_name)
        val itemPrice : TextView = itemView.findViewById(R.id.item_price)
        val itemPaymentOption : TextView = itemView.findViewById(R.id.item_payment_option)

    }
}