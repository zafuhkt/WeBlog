package com.example.weblog.ui.SuperActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.weblog.R
import com.example.weblog.databinding.ActivityTopicBinding
import com.example.weblog.ui.MainActivity.MainActivity
import com.example.weblog.ui.SelfActivity.self

@Suppress("DEPRECATION")
class topic : AppCompatActivity() {
    private lateinit var binding:ActivityTopicBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic)
        binding= ActivityTopicBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_topic)
        setContentView(binding.root)

        /*******************设置顶部状态栏字体颜色**********************/
        getWindow().getDecorView().systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        /**********************点击主页进行跳转***********************/
        binding.main.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        /*******************点击右下角我进行跳转*********************/
        binding.tvme.setOnClickListener{
            val intent = Intent(this, self::class.java)
            startActivity(intent)
        }
    }

}