package com.ayodkay.app.fulllabdesfio.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ayodkay.app.fulllabdesfio.R
import com.ayodkay.app.fulllabdesfio.adapter.SubCategoryAdapter
import com.ayodkay.app.fulllabdesfio.model.CategoryModel
import com.ayodkay.app.fulllabdesfio.model.SubCategoryModel
import kotlinx.android.synthetic.main.activity_sub_categories.*
import org.json.JSONObject
import java.util.ArrayList

class SubCategories : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_categories)

        category_name.text = intent.extras!!["categoryName"] as String


        sub_category_recycler.apply {

            layoutManager = LinearLayoutManager(this@SubCategories)
            adapter= SubCategoryAdapter(this@SubCategories,makeSubCategoryRequest())

        }


    }

    private fun makeSubCategoryRequest(): ArrayList<SubCategoryModel> {
        val queue = Volley.newRequestQueue(this)
        val url = "https://desafio.mobfiq.com.br/StorePreference/CategoryTree"
        val categoryList: ArrayList<SubCategoryModel> = ArrayList()


        // Request a string response from the provided URL.
        val stringRequest =object: StringRequest(
            Method.GET,url,
            Response.Listener<String> {responses->
                val jsonObj = JSONObject(responses!!)
                val categories = jsonObj.getJSONArray("Categories")

                for (i in 0 until categories.length()) {
                    val cat = categories.getJSONObject(i)
                    val subCategories = cat.getJSONArray("SubCategories")
                    for (j in 0 until subCategories.length()) {
                        val subCat = subCategories.getJSONObject(j)
                        val subName = subCat.getString("Name")
                        Log.d("categoryies2",subName)
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

        Log.d("categoryies3",categoryList.toString())

        return categoryList
    }
}
