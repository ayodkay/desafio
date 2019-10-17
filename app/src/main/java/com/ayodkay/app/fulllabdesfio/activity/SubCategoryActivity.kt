package com.ayodkay.app.fulllabdesfio.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.ayodkay.app.fulllabdesfio.R
import com.ayodkay.app.fulllabdesfio.adapter.SubCategoryAdapter
import com.ayodkay.app.fulllabdesfio.database.category.CategoryViewModel
import kotlinx.android.synthetic.main.activity_sub_categories.*

class SubCategoryActivity : AppCompatActivity() {

    private lateinit var categoryViewModel: CategoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_categories)

        sub_category_back.setOnClickListener {
            startActivity(Intent(this,CategoryActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP
                finish()
            })
        }

        category_name.text = intent.extras!!["categoryName"] as String

        val position = intent.extras!!["position"] as Int

        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel::class.java)

        val adapter = SubCategoryAdapter(this,position)

        sub_category_recycler.apply {
            layoutManager = LinearLayoutManager(this@SubCategoryActivity)
            setAdapter(adapter)

        }

        categoryViewModel.allCategory.observe(this, Observer { category ->
            category?.let { adapter.setCategory(it)}
        })
    }
}