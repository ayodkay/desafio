package com.ayodkay.app.fulllabdesfio.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ayodkay.app.fulllabdesfio.R
import com.ayodkay.app.fulllabdesfio.database.search.Search
import com.squareup.picasso.Picasso

class SearchAdapter internal constructor(private val context: Context):
    RecyclerView.Adapter<SearchAdapter.CategoryModels>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    private var allSearch = emptyList<Search>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryModels {
        val itemView = inflater.inflate(R.layout.list_item,parent,false)
        return CategoryModels(itemView)
    }

    override fun getItemCount(): Int {
        if (allSearch.isNullOrEmpty()){
            return 0
        }
        return allSearch.size
    }

    override fun onBindViewHolder(holder: CategoryModels, position: Int) {
        holder.itemName.text = allSearch[position].productName
        holder.itemPrice.text = allSearch[position].productPrice
        holder.itemPaymentOption.text = allSearch[position].productPaymentOption
        Picasso.get().load(allSearch[position].productImage).into(holder.itemIcon)
    }

    inner class CategoryModels(itemView: View): RecyclerView.ViewHolder(itemView){
        val itemIcon : ImageView = itemView.findViewById(R.id.item_image)
        val itemName : TextView = itemView.findViewById(R.id.item_name)
        val itemPrice : TextView = itemView.findViewById(R.id.item_price)
        val itemPaymentOption : TextView = itemView.findViewById(R.id.item_payment_option)

    }

    internal fun setSearch(allSearch: List<Search>){
        this.allSearch = allSearch
        notifyDataSetChanged()
    }
}