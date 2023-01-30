package com.example.newsfresh

import android.accessibilityservice.GestureDescription
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() , NewsItemClicked{
    private lateinit var mAdapter: NewsListAdapter//m is added before Adapter to make it accessible from multiple places.
    val newsArray = ArrayList<News>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        recyclerView.layoutManager=LinearLayoutManager(this)
        recyclerView.layoutManager=LinearLayoutManager(this)

        fetchData()
//        fetchData()
//        fetchData()
//        fetchData()
        mAdapter = NewsListAdapter(this)
        recyclerView.adapter=mAdapter


    }
//    private fun fetchData():ArrayList<String>{
//        val list = ArrayList<String>()
//        for(i in 0 until 100){
//            list.add("Item $i")
//        }
//        return list
//    }

    private fun fetchData(){
//        val url = "https://newsapi.org/v2/top-headlines?country=in&apiKey=e919b8e44f8843e9aa52ea6e776a5216"
        val url = "https://newsdata.io/api/1/news?apikey=pub_1157961733f1bfe407b0c0752c018ea9ae74b&q=india&q=coding&q=technology&q=programming&q=appdevelopment&q=google&q=microsoft&q=tcs&q=jobsInTechCompaniesForAppDeveloper"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener {
                val newsJsonArray = it.getJSONArray("results")
//                val newsArray = ArrayList<News>()
                for (i in 0 until newsJsonArray.length()){
                    val newsJSONObject = newsJsonArray.getJSONObject(i)
                    //parsing of the data :-
                    val news = News(
                        newsJSONObject.getString("title"),
//                        "jsdlkf",
                        newsJSONObject.getString("description"),
                        newsJSONObject.getString("link"),
                        newsJSONObject.getString("image_url"),
//                    "skljflk",
                    )
                    newsArray.add(news)
                }
                mAdapter.updateNews(newsArray)
            },
            Response.ErrorListener {

            }
        )

//        @Override
//        public Map<String, String> getHeaders() throws AuthFailureError {
//            HashMap<String, String> headers = new HashMap<String, String>();
//            headers.put("User-Agent", "Mozilla/5.0");
//            return headers;
//        }

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onItemClicked(item: News) {
//        Toast.makeText(this,"clicked item no = $item",Toast.LENGTH_SHORT).show()
//        val url = "https://google.com/"
        val builder: CustomTabsIntent.Builder = CustomTabsIntent.Builder()
        val customTabsIntent: CustomTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))
    }


}