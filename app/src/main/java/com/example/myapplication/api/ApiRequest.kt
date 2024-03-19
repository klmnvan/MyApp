package com.example.myapplication.api

import com.example.myapplication.models.Catalog
import retrofit2.Call
import retrofit2.http.GET

interface ApiRequest {

    //здесь описание запроса к api
    @GET("api/Catalog")
    fun getCatalog(): Call<List<Catalog>>

}