package com.example.weblog

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import java.lang.Exception
import java.net.URLEncoder
import kotlin.concurrent.thread
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request


class LoginActivity : AppCompatActivity() {
    val SERVER_IP="192.168.226.18"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
//tv.text=R.drawable.test.toString()
        var LoginResult=""
        btn_login.setOnClickListener {
            thread {
                if(!cb_agree.isChecked)
                {
                    //未同意协议
                    runOnUiThread {
                        Toast.makeText(this,"登录微博请先阅读并同意用户协议、隐私条款" +
                                "和天翼认证账号服务协议",Toast.LENGTH_LONG).show()
                    }
                }
                else {
                    try {
                        var server_url =
                            "http://${SERVER_IP}:8081/WeBlog_war_exploded/User_login.jsp"
                        val client = OkHttpClient()
                        val requestBody = FormBody.Builder()
                            .add("id", tv_user_id.text.toString())
                            .add("password", tv_user_pwd.text.toString())
                            .build()
                        val request = Request.Builder()
                            .url(server_url)
                            .post(requestBody)
                            .build()
                        val response = client.newCall(request).execute()
                        val responseData = response.body?.string()
                        if (responseData != null) {
                            LoginResult=responseData.trim()
                            runOnUiThread {
                                if (LoginResult == "Login successfully" || LoginResult=="Newuser") {
                                    val intentmain = Intent(this, MainActivity::class.java)
                                    intentmain.putExtra("main_user_id", tv_user_id.text.toString())
                                    startActivity(intentmain)
                                }
                                else if(LoginResult == "Password incorrect")
                                {
                                    Toast.makeText(this,"密码错误",Toast.LENGTH_LONG).show()
                                }
                            }
                        }
                        else{
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
            //线程外

        }
        close.setOnClickListener{
            finish()
        }
    }
}