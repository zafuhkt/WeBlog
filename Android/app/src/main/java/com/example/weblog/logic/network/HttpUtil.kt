package com.example.weblog.logic.network

import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import com.example.weblog.ui.MainActivity.ViewModel.blog_TextViewModel
object HttpUtil {
    fun sendOkHttpRequest(address: String, callback: okhttp3.Callback){
        val client = OkHttpClient()
        val requestBody = FormBody.Builder()
            .add("main_user_id", "123")
            .build()
        val request = Request.Builder()
            .url(address)
            .post(requestBody)
            .build()
        client.newCall(request).enqueue(callback)
    }
    fun sendOkHttpRequestLoad(address: String, id: Int, callback: okhttp3.Callback){
        val client = OkHttpClient()
        val requestBody = FormBody.Builder()
            .add("blog_id", id.toString())
            .build()
        val request = Request.Builder()
            .url(address)
            .post(requestBody)
            .build()
        client.newCall(request).enqueue(callback)
    }
}