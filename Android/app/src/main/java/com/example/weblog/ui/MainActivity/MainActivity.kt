package com.example.weblog.ui.MainActivity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weblog.R
import com.example.weblog.databinding.ActivityMainBinding
import com.example.weblog.logic.model.main_elem_Info
import com.example.weblog.logic.network.HttpUtil
import com.example.weblog.ui.MainActivity.ViewModel.MainViewModel
import com.example.weblog.ui.SelfActivity.self
import com.example.weblog.ui.SuperActivity.topic
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_elem.view.*
import okhttp3.*
import org.json.JSONArray


private val mainList = ArrayList<main_elem_Info>()
var resourcesInstance: Resources? = null

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
        setContentView(binding.root)
        resourcesInstance = this.getResources()
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        /*******************设置顶部状态栏字体颜色*********************/
        getWindow().getDecorView().systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        val button_Me = binding.me
        //读取上次登录
        val prefs = getSharedPreferences("Record", Context.MODE_PRIVATE)
        viewModel.main_user_id = prefs.getInt("user_id", 0)
        //从登录页面接收到返回值
        if(intent.getStringExtra("main_user_id")!=null)
        {
            viewModel.main_user_id=intent.getStringExtra("main_user_id")!!.toInt()
            //登录后 用户id存入Record
            val editor = getSharedPreferences("Record", Context.MODE_PRIVATE).edit()
            editor.putInt("user_id", viewModel.main_user_id)
            editor.apply()
            load()
        }else
        {
            if (viewModel.main_user_id == 0) {
                camera.setImageResource(R.drawable.login)
                camera.setOnClickListener {
                    val intentlogin = Intent(this, LoginActivity::class.java)
                    startActivity(intentlogin)
                }
            }
            else{
                load()
             }
        }
            /**********************recyclerView的装配******************/
            val mainElemRecyclerView = binding.mainblogRecyclerView
            val layoutManager = LinearLayoutManager(this)
            mainElemRecyclerView.layoutManager = layoutManager
            val adapter = MainAdapter(mainList)
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
            adapter.setOnItemClickListener(object : MainAdapter.OnItemClickListener {
                override fun onclick(v: View, position: Int) {
                    mainActivityElemClickLeap.launch(mainList[position])
                    viewModel.pos = position
                }
            })
        }
        /**********************根据图片名获取id*********************/
        fun getResourceId(imageName: String): Int {
            val ctx = baseContext
            val resId = resources.getIdentifier(imageName, "drawable", ctx.packageName)
            return resId
        }
    private fun load() {
        HttpUtil.sendOkHttpRequest("http://${viewModel.server_ip}:8081/WeBlog_war_exploded/Blog_find.jsp", object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()
                runOnUiThread {
                    if (responseData != null) {
                        parseJson(responseData.trim())
                    }
                }
            }
            override fun onFailure(call: okhttp3.Call, e: okio.IOException) {
                TODO("Not yet implemented")
            }
        })
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
                val text = jsonObject.getString("Text")
                val head = jsonObject.getString("Image")
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
//    @SuppressLint("NotifyDataSetChanged", "SuspiciousIndentation")
//    private fun load() {
//    mainList.clear()
//    mainblogRecyclerView.adapter?.notifyDataSetChanged()
//    val mainService = ServiceCreator.create(MainService::class.java)
//        mainService.getMainData("1").enqueue(object :
//        retrofit2.Callback<List<main_elem_Info>>{
//            override fun onResponse(
//                call: Call<List<main_elem_Info>>,
//                response: Response<List<main_elem_Info>>
//            ) {
//                val list = response.body()
//                if (list != null) {
//                    for (info in list) {
//                        mainList.add(info)
//                    }
//                }
//            }
//            override fun onFailure(call: Call<List<main_elem_Info>>, t: Throwable) {
//                t.printStackTrace()
//            }
//        })
//    }
    private val mainActivityElemClickLeap =
        registerForActivityResult(MainActivityContract()) { result ->
        }

    class mainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.tvPicture)
        val name: TextView = view.findViewById(R.id.tvName)
        val time: TextView = view.findViewById(R.id.tvTime)
        val place: TextView = view.findViewById(R.id.tvPlace)
        val text: TextView = view.findViewById(R.id.tvText)
        val relay: TextView = view.findViewById(R.id.tvRelay)
        val comment: TextView = view.findViewById(R.id.tvComment)
        val thumbup: TextView = view.findViewById(R.id.tvThumbup)
    }
}