package com.ayodkay.app.fulllabdesfio.activity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.GridLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ayodkay.app.fulllabdesfio.R
import com.ayodkay.app.fulllabdesfio.adapter.SearchAdapter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        product_recycler.apply {
//            layoutManager = GridLayoutManager(this@MainActivity,2)
//            adapter = SearchAdapter(this@MainActivity)
//        }

        menu.setOnClickListener {
            startActivity(Intent(this,Categories::class.java))
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

    fun makeSearchRequest(queryString: String){
        val queue = Volley.newRequestQueue(this)
        val url = "https://desafio.mobfiq.com.br/Search/Criteria"

        // Request a string response from the provided URL.
        val stringRequest =object : StringRequest(
            Method.POST, url,
            Response.Listener<String> { response ->
                product.text = "Response is: $response"

            },
            Response.ErrorListener { product.text = "That didn't work!" }){

            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["Query"] = queryString
                params["Offset"] = "0"
                params["Size"] = "10"

                return params
            }

        }

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }
}
