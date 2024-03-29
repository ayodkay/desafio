package com.ayodkay.app.fulllabdesfio.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ayodkay.app.fulllabdesfio.R
import com.ayodkay.app.fulllabdesfio.adapter.CategoryAdapter
import com.ayodkay.app.fulllabdesfio.database.category.Categories
import com.ayodkay.app.fulllabdesfio.database.category.CategoryViewModel
import kotlinx.android.synthetic.main.activity_categories.*
import org.json.JSONObject


class CategoryActivity : AppCompatActivity() {

    private lateinit var categoryViewModel: CategoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)

        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel::class.java)

        val adapter = CategoryAdapter(this)

        //got back to previous activity
        category_back_bt.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP
                finish()
            })
        }

        //handles the recyclerView for CategoryActivity
        recycle_categories.apply {
            layoutManager = LinearLayoutManager(this@CategoryActivity)
            setAdapter(adapter)
        }


        //create an observer for all category
        categoryViewModel.allCategory.observe(this, Observer { category ->
            category?.let { adapter.setCategory(it)

                if(it.isEmpty()){
                    makeRequest()
                }else{
                    recycle_categories.visibility = View.VISIBLE
                    category_progress.visibility = View.GONE
                }
            }
        })

    }


    //makes a request to given url and return a json in string format using volley
    private fun makeRequest() {
        val queue = Volley.newRequestQueue(this)
        val url = "https://desafio.mobfiq.com.br/StorePreference/CategoryTree"

        // Request a string response from the provided URL.
        val stringRequest =object: StringRequest(
            Method.GET,url,
            Response.Listener<String> {response->
                handleJson(response)
            },
            Response.ErrorListener {error ->
                Toast.makeText(this,"Response error: ${error.cause.toString()}",
                    Toast.LENGTH_LONG).show() }){

            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }
        }


        // Add the request to the RequestQueue.
        queue.add(stringRequest)

    }


    // handles the response from request made in the function above
    private fun handleJson(response: String){

        val jsonObj = JSONObject(response)
        val categories = jsonObj.getJSONArray("Categories")

        for (i in 0 until categories.length()) {
            val cat = categories.getJSONObject(i)
            val name = cat.getString("Name")
            val subCategories = cat.getJSONArray("SubCategories")
            val subArray : ArrayList<String> = ArrayList()
            for (j in 0 until subCategories.length()) {
                val subCat = subCategories.getJSONObject(j)
                val subName = subCat.getString("Name")
                subArray.add(subName)
            }

            categoryViewModel.insert(Categories(
                    name,
                    subArray
                )
            )
        }
    }

}
