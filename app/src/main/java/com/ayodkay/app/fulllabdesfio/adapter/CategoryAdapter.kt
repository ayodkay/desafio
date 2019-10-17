package com.ayodkay.app.fulllabdesfio.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ayodkay.app.fulllabdesfio.R
import com.ayodkay.app.fulllabdesfio.activity.SubCategoryActivity
import com.ayodkay.app.fulllabdesfio.database.category.Categories

class CategoryAdapter internal constructor(private val context: Context):
    RecyclerView.Adapter<CategoryAdapter.CategoryModels>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    private var allCategory = emptyList<Categories>()

    private var myImageList = intArrayOf(
        R.drawable.ic_beleza, R.drawable.ic_casa,R.drawable.ic_electropotatais, R.drawable.ic_bebe,
        R.drawable.ic_pet, R.drawable.ic_be_emotion,R.drawable.ic_viva, R.drawable.ic_modeladores_corporais,
        R.drawable.ic_electronicos, R.drawable.ic_fitness,R.drawable.ic_saude_bem_esta, R.drawable.ic_cuisinart,
        R.drawable.ic_hobby_lazer)


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

        holder.itemView.setOnClickListener {
            context.startActivity(Intent(context,SubCategoryActivity::class.java).apply {
                putExtra("categoryName",allCategory[position].categoryName)
                putExtra("position",position)
            })
        }

        holder.itemImage.setImageResource(myImageList[position])
    }

    inner class CategoryModels(itemView: View): RecyclerView.ViewHolder(itemView){
        val itemName : TextView = itemView.findViewById(R.id.category_name)
        val itemImage : ImageView = itemView.findViewById(R.id.category_image)
    }

    internal fun setCategory(allCategory: List<Categories>){
        this.allCategory = allCategory
        notifyDataSetChanged()
    }
}