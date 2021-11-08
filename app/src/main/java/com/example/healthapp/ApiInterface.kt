package com.example.healthapp


import com.example.healthapp.model.News
import retrofit2.http.GET

interface ApiInterface {
    @GET("/v2/top-headlines?country=ca&category=health&apiKey=7a4fdaa8ee1e4d14b2c8e40491da1cee")
    suspend fun getNews(): News
}