package com.example.weblog

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.TypedArrayUtils.getResourceId
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weblog.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_elem.view.*
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader
import java.lang.Exception
import java.net.URLEncoder
import kotlin.concurrent.thread

private val mainList = ArrayList<main_elem_Info>()
var pos = 0
var main_user_id=0

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    /**************************服务端地址***************************/
    val server_ip = "192.168.226.18"
   // val search_main_elem = "http://${server_ip}:8081/WeBlog_war_exploded/get_main_elem.jsp"

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
        setContentView(binding.root)
        /*******************设置顶部状态栏字体颜色*********************/
        getWindow().getDecorView().systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        val button_Me = binding.me
        val button_add_main = binding.addMain

//读取上次登录
        val prefs = getSharedPreferences("Record", Context.MODE_PRIVATE)
        main_user_id = prefs.getInt("user_id", 0)

//从登录页面接收到返回值
        if(intent.getStringExtra("main_user_id")!=null)
        {
            main_user_id=intent.getStringExtra("main_user_id")!!.toInt()
            //登录后 用户id存入Record
            val editor = getSharedPreferences("Record", Context.MODE_PRIVATE).edit()
            editor.putInt("user_id", main_user_id)
            editor.apply()
            load()
        }else
        {
            if (main_user_id == 0) {
                camera.setImageResource(R.drawable.login)
                camera.setOnClickListener {
                    val intentlogin = Intent(this, LoginActivity::class.java)
                    startActivity(intentlogin)
                }
            }//从Record中读到数据123
            else{
                load()
             }
        }

            /**********************recyclerView的装配******************/
            val mainElemRecyclerView = binding.mainblogRecyclerView
            val layoutManager = LinearLayoutManager(this)
            mainElemRecyclerView.layoutManager = layoutManager
            val adapter = mainAdapter(mainList)
            mainElemRecyclerView.adapter = adapter

            /*******************点击右下角我进行跳转*********************/
            button_Me.setOnClickListener {
                val intent = Intent(this, self::class.java)
                startActivity(intent)
            }

            /*******************点击左下角超话进行跳转********************/
            binding.topic1.setOnClickListener {
                val intent = Intent(this, topic::class.java)
                startActivity(intent)
            }

            /*******************点击评论数我进行跳转*********************/
            adapter.setOnItemClickListener(object : mainAdapter.OnItemClickListener {
                override fun onclick(v: View, position: Int) {
                    mainActivityElemClickLeap.launch(mainList[position])
                    pos = position
                }
            })
        }

        /**********************根据图片名获取id*********************/
        private fun getResourceId(imageName: String): Int {
            val ctx = baseContext
            val resId = resources.getIdentifier(imageName, "drawable", ctx.packageName)
            return resId;
        }

    @SuppressLint("Range", "NotifyDataSetChanged")
    private fun load(){
        thread {
            try {
                var server_url =
                    "http://${server_ip}:8081/WeBlog_war_exploded/Blog_find.jsp"
                val client = OkHttpClient()
                val requestBody = FormBody.Builder()
                    .add("main_user_id", "123")  //main_user_id.toString())
                    .build()
                val request = Request.Builder()
                    .url(server_url)
                    .post(requestBody)
                    .build()
                val response = client.newCall(request).execute()
                val responseData = response.body?.string()
                if (responseData != null) {
                    runOnUiThread {
                        parseJson(responseData.trim())
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
        @SuppressLint("NotifyDataSetChanged", "SuspiciousIndentation")
        private fun parseJson(jsonStr: String) {
            mainList.clear()
            mainblogRecyclerView.adapter?.notifyDataSetChanged()
            try {
                val jsonArray = JSONArray(jsonStr)
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val id = jsonObject.getInt("id")
                    val user_id = jsonObject.getInt("user_id")
                    val name = jsonObject.getString("name")
                    val time = jsonObject.getString("time")
                    val place = jsonObject.getString("place")
                    val text = jsonObject.getString("text")
                    val head = jsonObject.getString("head")
                    val relay = jsonObject.getInt("relay")
                    val comment = jsonObject.getInt("comment")
                    val thumbup = jsonObject.getInt("thumbup")
                    val elemInfo = main_elem_Info(
                        id,
                        user_id,
                        getResourceId(head),
                        name,
                        time,
                        place,
                        text,
                        relay,
                        comment,
                        thumbup
                    )
                    mainList.add(elemInfo)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        /********************主页条目跳转协议注册*********************/
        private val mainActivityElemClickLeap =
            registerForActivityResult(MainActivityContract()) { result ->
            }

        /**********************主页面的Holder**********************/
        private class mainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val image: ImageView = view.findViewById(R.id.tvPicture)
            val name: TextView = view.findViewById(R.id.tvName)
            val time: TextView = view.findViewById(R.id.tvTime)
            val place: TextView = view.findViewById(R.id.tvPlace)
            val text: TextView = view.findViewById(R.id.tvText)
            val relay: TextView = view.findViewById(R.id.tvRelay)
            val comment: TextView = view.findViewById(R.id.tvComment)
            val thumbup: TextView = view.findViewById(R.id.tvThumbup)
        }

        /**********************主页面适配器************************/
        private class mainAdapter(val mainList: List<main_elem_Info>) :
            RecyclerView.Adapter<mainViewHolder>() {
            interface OnItemClickListener {
                fun onclick(v: View, position: Int)
            }

            private var onItemClickListener: OnItemClickListener? = null
            fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
                this.onItemClickListener = onItemClickListener
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): mainViewHolder {
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.main_elem, parent, false)
                return mainViewHolder(view)
            }

            override fun onBindViewHolder(holder: mainViewHolder, position: Int) {
                val info = mainList[position]
                holder.image.setImageResource(info.Image)
                holder.name.text = info.name
                holder.time.text = info.time
                holder.place.text = info.place
                holder.text.text = info.Text
                holder.relay.text = info.relay.toString()
                holder.comment.text = info.comment.toString()
                holder.thumbup.text = info.thumbUp.toString()
                if (onItemClickListener != null) {
                    holder.itemView.tvComment.setOnClickListener {
                        onItemClickListener?.onclick(
                            holder.itemView.tvComment,
                            position
                        )
                    }
                }
            }
            override fun getItemCount() = mainList.size
        }
    }
