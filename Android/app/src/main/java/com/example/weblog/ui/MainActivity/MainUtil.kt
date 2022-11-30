package com.example.weblog.ui.MainActivity

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.example.weblog.logic.model.main_elem_Info

/**********************主页条目跳转协议*********************/
class MainActivityContract: ActivityResultContract<main_elem_Info, main_elem_Info>() {
    override fun createIntent(context: Context, input: main_elem_Info?): Intent {
        return Intent(context, blog_Text::class.java).apply {
            putExtra("receiveMainInfo",input)
        }
    }
    override fun parseResult(resultCode: Int, intent: Intent?): main_elem_Info? {
        val data = intent?.getParcelableExtra<main_elem_Info?>("result")
        return if (resultCode == Activity.RESULT_OK && data != null) data
        else null
    }
}