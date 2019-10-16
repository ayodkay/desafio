package com.ayodkay.app.fulllabdesfio.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Response
import com.ayodkay.app.fulllabdesfio.R
import com.ayodkay.app.fulllabdesfio.adapter.CategoryAdapter
import com.ayodkay.app.fulllabdesfio.model.CategoryModel
import kotlinx.android.synthetic.main.activity_categories.*
import org.json.JSONObject
import java.util.ArrayList
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley




class Categories : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)


        category_back_bt.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP
                finish()
            })
        }

        recycle_categories.apply {
            layoutManager = LinearLayoutManager(this@Categories)
            adapter =  CategoryAdapter(this@Categories,makeCategoryRequest())

        }
    }

    private fun makeCategoryRequest(): ArrayList<CategoryModel> {
        val queue = Volley.newRequestQueue(this)
        val url = "https://desafio.mobfiq.com.br/StorePreference/CategoryTree"
        val categoryList: ArrayList<CategoryModel> = ArrayList()


        // Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET,url,
            Response.Listener<String?> { response->
                val jsonObj = JSONObject(response!!)
                val categories = jsonObj.getJSONArray("Categories")

                Log.d("categoryies",categories.toString())

                for (i in 0 until categories.length()) {
                    val cat = categories.getJSONObject(i)
                    val name = cat.getString("SubCategories")
                    categoryList.add(CategoryModel(name))

                }
            },
            Response.ErrorListener { error -> Log.d("Category Adapter","Response error: ${error.message}") })
        // Add the request to the RequestQueue.
        queue.add(stringRequest)

        Log.d("category",categoryList.toString())

        return categoryList
    }

}
