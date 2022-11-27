package com.example.weblog

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.weblog.databinding.ActivityMainBinding
import com.example.weblog.databinding.ActivitySelfBinding


@Suppress("DEPRECATION")
class self : AppCompatActivity() {
    private lateinit var binding:ActivitySelfBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_self)
        binding= ActivitySelfBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_self)
        setContentView(binding.root)

        /*******************设置顶部状态栏字体颜色*********************/
        getWindow().getDecorView().systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        /**********************点击主页进行跳转***********************/
        binding.main.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        /*******************点击左下角超话进行跳转********************/
        binding.topic1.setOnClickListener{
            val intent = Intent(this,topic::class.java)
            startActivity(intent)
        }

    }
}