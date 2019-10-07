package com.example.newsletter

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log.d
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org")
            .addConverterFactory(GsonConverterFactory.create()) // pour convertir notre objet JSON
            .build() // pour setup RetroFit

        val articleApi = retrofit.create(ArticleService::class.java) // on passe notre class Article service pour fetcher tous les articles

        articleApi.fetchAllArticles().enqueue(object : Callback<Articles>{ // la methode qui s'occupe de fetcher tous les articles

            override fun onResponse(call: Call<Articles>, response: Response<Articles>) {

                if(response.body()!=null){
                showData(response.body()!!.articles)
                }
                else {
                    val builder = AlertDialog.Builder(this@MainActivity)
                    builder.setMessage("Please update the url of the Api")
                    val dialog: AlertDialog = builder.create()

                    // Display the alert dialog on app interface
                    dialog.show()
                }
            }
            override fun onFailure(call: Call<Articles>, t: Throwable) {
                d("title","onFailure "+t)
            }
        })


      /*  val articles = mutableListOf<Article>()
        for(i in 0..50){
            articles.add(Article("Realmadrid","best club of the century",""))
        }
*/

    }

    private fun showData(articles: List<Article>) {

        recyclerView.apply {
            layoutManager= LinearLayoutManager(this@MainActivity)
            adapter = ArticlesAdapter(articles)
        }

    }


}
