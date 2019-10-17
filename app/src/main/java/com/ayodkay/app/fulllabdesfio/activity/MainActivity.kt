package com.ayodkay.app.fulllabdesfio.activity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ayodkay.app.fulllabdesfio.R
import com.ayodkay.app.fulllabdesfio.adapter.SearchAdapter
import com.ayodkay.app.fulllabdesfio.database.search.Search
import com.ayodkay.app.fulllabdesfio.database.search.SearchViewModel
import com.ayodkay.app.fulllabdesfio.helper.EndlessRecyclerViewScrollListener
import com.ayodkay.app.fulllabdesfio.helper.SpacesItemDecoration
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var searchViewModel: SearchViewModel
    private var scrollListener: EndlessRecyclerViewScrollListener? = null


    private lateinit var currentQuery: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
        val adapter = SearchAdapter(this@MainActivity)


        //handles infinity scroll
        scrollListener = object : EndlessRecyclerViewScrollListener((
                GridLayoutManager(this@MainActivity,2))){

            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                makeSearchRequest(false)
            }
        }

        // handles recyclerView for MainActivity
        product_recycler.apply {
            layoutManager = GridLayoutManager(this@MainActivity,2)
            setAdapter(adapter)
            addItemDecoration(SpacesItemDecoration(6))
            addOnScrollListener(scrollListener!!)
        }

        //opens the CategoryActivity
        menu.setOnClickListener {
            startActivity(Intent(this,CategoryActivity::class.java))
        }

        //makes search query and makes request from the query
        search.apply {
            setOnSearchClickListener {it.setBackgroundColor(Color.parseColor("#ffffff"))}
            setOnCloseListener { this.setBackgroundColor(Color.parseColor("#000000"))
                false}

            setOnQueryTextListener(object:androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query == null){
                        return false
                    }else{
                        currentQuery = query
                        makeSearchRequest(true,query)

                    }

                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    product.text = getString(R.string.products)
                    return true
                }

            })
        }

        //create an observer for all search
        searchViewModel.allSearch.observe(this, Observer { category ->
            category?.let { adapter.setSearch(it)

                if(it.isNullOrEmpty()){
                    makeSearchRequest(false)
                }else{
                    progress_main.visibility = View.GONE
                }
            }
        })

    }

    //makes a request to given url and return a json in string format using volley
    private fun makeSearchRequest(isSearch :Boolean,queryString: String?=null){
        val queue = Volley.newRequestQueue(this)
        val url = "https://desafio.mobfiq.com.br/Search/Criteria"

        // Request a string response from the provided URL.
        val stringRequest =object : StringRequest(
            Method.POST, url,
            Response.Listener<String> { response ->

                if (isSearch){
                    searchViewModel.nuke()
                    product.text =getString(R.string.search_result)
                }

                handleJson(response)
            },
            Response.ErrorListener { Toast.makeText(this@MainActivity, it.message.toString(),Toast.LENGTH_LONG).show()}){

            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                if (queryString.isNullOrEmpty()){
                    params["Query"] = ""
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
    }


    // handles the response from request made in the function above
    private fun handleJson(response :String){
        val jsonObj = JSONObject(response)
        val search = jsonObj.getJSONArray("Products")

        if (search.isNull(0)){
            Toast.makeText(this@MainActivity,"no product available",Toast.LENGTH_LONG).show()
        }else{
            for(i in 0 until search.length()){
                var name :String? = null
                var price :String? = null
                var image : String? = null
                var paymentInstallment : String? = null

                val cat = search.getJSONObject(i)
                val skus = cat.getJSONArray("Skus")
                for(Skus in 0 until skus.length()){
                    val subCat = skus.getJSONObject(Skus)

                    name = subCat.getString("Name")
                    val priceArray = subCat.getJSONArray("Sellers")
                    for (sellers in 0 until priceArray.length()){
                        val priceObj  = priceArray.getJSONObject(sellers)
                        price = "R$ ${priceObj.getString("Price")}"
                        
                       try {
                           val paymentOptionArray = priceObj.getJSONObject("BestInstallment")
                           val count = paymentOptionArray.getString("Count")
                           val value = paymentOptionArray.getString("Value")
                           if(!count.isNullOrEmpty()){
                               paymentInstallment = "$count x de $value"
                           }
                       }catch (js: JSONException){
                           continue
                       }


                    }
                    val imageArray = subCat.getJSONArray("Images")
                    val imageobj  = imageArray.getJSONObject(0)
                    image = imageobj.getString("ImageTag")
                }
                searchViewModel.insert(Search(name!!,price!!,image!!,paymentInstallment))
            }
        }
    }


}
