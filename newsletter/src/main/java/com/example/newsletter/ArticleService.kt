package com.example.newsletter

import retrofit2.Call
import retrofit2.http.GET

interface ArticleService {

    @GET("/v2/everything?q=bitcoin&from=2019-09-06&sortBy=publishedAt&apiKey=c8bc71aef5f749a5ad1b558fa8e41447")
    fun fetchAllArticles(): Call<Articles>


}