package com.ayodkay.app.fulllabdesfio.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ayodkay.app.fulllabdesfio.R
import com.ayodkay.app.fulllabdesfio.adapter.CategoryAdapter
import com.ayodkay.app.fulllabdesfio.database.Categories
import com.ayodkay.app.fulllabdesfio.database.CategoryViewModel
import com.ayodkay.app.fulllabdesfio.model.CategoryModel
import kotlinx.android.synthetic.main.activity_categories.*
import org.json.JSONObject


class CategoryActivity : AppCompatActivity() {

    private lateinit var categoryViewModel: CategoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)


        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel::class.java)

        val adapter = CategoryAdapter(this)

        category_back_bt.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP
                finish()
            })
        }

        recycle_categories.apply {
            layoutManager = LinearLayoutManager(this@CategoryActivity)
            setAdapter(adapter)
        }


        categoryViewModel.allCategory.observe(this, Observer { category ->
            category?.let { adapter.setCategory(it)
                if(it.isEmpty()){
                    makeRequest()
                }
            }
        })

    }

    private fun makeRequest(): java.util.ArrayList<CategoryModel> {
        val queue = Volley.newRequestQueue(this)
        val url = "https://desafio.mobfiq.com.br/StorePreference/CategoryTree"
        val categoryList: java.util.ArrayList<CategoryModel> = java.util.ArrayList()


        // Request a string response from the provided URL.
        val stringRequest =object: StringRequest(
            Method.GET,url,
            Response.Listener<String> {responses->
                val jsonObj = JSONObject(responses!!)
                val categories = jsonObj.getJSONArray("Categories")

                for (i in 0 until categories.length()) {
                    val cat = categories.getJSONObject(i)
                    val name = cat.getString("Name")
                    val subCategories = cat.getJSONArray("SubCategories")
                    for (j in 0 until subCategories.length()) {
                        val subCat = subCategories.getJSONObject(j)
                        val subName = subCat.getString("Name")

                        val subArray : ArrayList<String> = ArrayList()

                        subArray.add(subName)

                        categoryViewModel.insert(Categories(name,subArray))
                    }
                }
            },
            Response.ErrorListener {error -> Log.d("Category Adapter","Response error: ${error.message}") }){

            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }
        }


        // Add the request to the RequestQueue.
        queue.add(stringRequest)

        return categoryList
    }

}
