package com.example.weblog

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class WeBlogApplication : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}