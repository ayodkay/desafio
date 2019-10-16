package com.ayodkay.app.fulllabdesfio.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.ayodkay.app.fulllabdesfio.R
import com.ayodkay.app.fulllabdesfio.adapter.SubCategoryAdapter
import com.ayodkay.app.fulllabdesfio.database.CategoryViewModel
import kotlinx.android.synthetic.main.activity_sub_categories.*

class SubCategories : AppCompatActivity() {

    private lateinit var categoryViewModel: CategoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_categories)

        category_name.text = intent.extras!!["categoryName"] as String

        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel::class.java)

        val adapter = SubCategoryAdapter(this)


        sub_category_recycler.apply {
            layoutManager = LinearLayoutManager(this@SubCategories)
            setAdapter(adapter)

        }

        categoryViewModel.allCategory.observe(this, Observer { category ->
            category?.let { adapter.setCategory(it)}
        })
    }
}