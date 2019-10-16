package com.ayodkay.app.fulllabdesfio.activity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ayodkay.app.fulllabdesfio.R
import com.ayodkay.app.fulllabdesfio.adapter.SearchAdapter
import com.ayodkay.app.fulllabdesfio.model.SearchModel
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        product_recycler.apply {
            layoutManager = GridLayoutManager(this@MainActivity,2)
            adapter = SearchAdapter(this@MainActivity,makeSearchRequest())
        }

        makeSearchRequest()

        menu.setOnClickListener {
            startActivity(Intent(this,CategoryActivity::class.java))
        }

        search.apply {
            setOnSearchClickListener {it.setBackgroundColor(Color.parseColor("#ffffff"))}
            setOnCloseListener { this.setBackgroundColor(Color.parseColor("#000000"))
            false}

            setOnQueryTextListener(object:androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query == null){
                        return false
                    }else{
                        makeSearchRequest(query)
                    }

                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    product.text = ""
                    return true
                }

            })
        }

    }

    fun makeSearchRequest(queryString: String?=null):ArrayList<SearchModel>{
        val queue = Volley.newRequestQueue(this)
        val url = "https://desafio.mobfiq.com.br/Search/Criteria"
        val searchList : ArrayList<SearchModel> = ArrayList()

        // Request a string response from the provided URL.
        val stringRequest =object : StringRequest(
            Method.POST, url,
            Response.Listener<String> { response ->
                val jsonObj = JSONObject(response)
                val categories = jsonObj.getJSONArray("Products")

                if (categories.isNull(0)){
                    Toast.makeText(this@MainActivity,"no product available",Toast.LENGTH_LONG).show()
                }else{
                    for(i in 0 until categories.length()){
                        var name :String? = null
                        var price :String? = null
                        var image : String? = null


                        val cat = categories.getJSONObject(i)
                        val skus = cat.getJSONArray("Skus")
                        for(Skus in 0 until skus.length()){
                            val subCat = skus.getJSONObject(Skus)
                            name = subCat.getString("Name")
                            val priceArray = subCat.getJSONArray("Sellers")
                            for (sellers in 0 until priceArray.length()){
                                val priceObj  = priceArray.getJSONObject(sellers)
                                price = priceObj.getString("Price")
                            }
                            val imageArray = subCat.getJSONArray("Images")
                            for (images in 0 until imageArray.length()){
                                val imageobj  = imageArray.getJSONObject(images)
                                image = imageobj.getString("ImageUrl")
                            }


                           searchList.add(i, SearchModel(name,price!!,image!!))
                        }
                    }
                }
            },
            Response.ErrorListener { product.text = "That didn't work!" }){

            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                if (queryString.isNullOrEmpty()){
                    params["Query"] = "roupa"
                }else{
                    params["Query"] = queryString
                }
                params["Offset"] = "0"
                params["Size"] = "10"

                return params
            }

        }

        // Add the request to the RequestQueue.
        queue.add(stringRequest)

        return searchList
    }
}
