package com.example.weblog.logic.network

import com.example.weblog.logic.model.main_elem_Info
import retrofit2.Call
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MainService {
    //@FormUrlEncoded
    @POST("Blog_find.jsp")
    fun getMainData(@Query("main_user_id") main_user_id:String): Call<List<main_elem_Info>>
}