package com.example.weblog

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weblog.databinding.ActivityBlogTextBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_blog_text.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.detail_elem.*
import kotlinx.android.synthetic.main.main_elem.view.*
import kotlinx.android.synthetic.main.relay_dialog_layout.*
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import java.net.URLEncoder
import kotlin.concurrent.thread


private var ListDetail = ArrayList<main_elem_Info>()
private var CommentDetail = ArrayList<comment_elem>()
private var ThumbUpDetail = ArrayList<thumbUp_elem>()
private var temp_agree = 1

var blog_id = 0
var blog_user_id = 0
@Suppress("DEPRECATION")
class blog_Text : AppCompatActivity() {

    /**********************sqlite3的helper************************/
    private val dbHelper = MyDatabaseHelper(this, "MainElem.db", 1)

    /**************************服务端地址***************************/
    val server_ip = "192.168.226.18"
    val search_thumbup_elem = "http://${server_ip}:8081/WeBlog_war_exploded/get_agree_elem.jsp"

    private lateinit var binding: ActivityBlogTextBinding
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBlogTextBinding.inflate(layoutInflater)
        /******************评论时不让软键盘影响整体布局******************/
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_blog_text)
        setContentView(binding.root)

        /*******************设置顶部状态栏字体颜色*********************/
        getWindow().getDecorView().systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        val receiveElemInfo = intent.getParcelableExtra<main_elem_Info>("receiveMainInfo")
        if (receiveElemInfo != null) {
            ListDetail.clear()
            val id = receiveElemInfo.id
            blog_id = id
            val Image = receiveElemInfo.Image
            val user_id=receiveElemInfo.user_id
            blog_user_id = user_id
            val name = receiveElemInfo.name
            val time = receiveElemInfo.time
            val place = receiveElemInfo.place
            val Text = receiveElemInfo.Text
            val relay = receiveElemInfo.relay
            val comment = receiveElemInfo.comment
            val thumbUp = receiveElemInfo.thumbUp
            val elemInfo = main_elem_Info(id, user_id, Image, name, time, place, Text, relay, comment, thumbUp)
            ListDetail.add(elemInfo)
        }

        binding.back.setOnClickListener {
            finish()
        }

        /**********************初始化页面的blog*********************/
        load_comment()
        load_thumbUp()

        /**********************recyclerView的装配******************/
        val commentElemRecyclerView = commentRecyclerView
        val detaillayoutManager = LinearLayoutManager(this)
        commentElemRecyclerView.layoutManager = detaillayoutManager
        val commentadapter = CommentAdapter(CommentDetail)
        commentElemRecyclerView.adapter = commentadapter

        /**********************recyclerView的装配******************/
        val detailElemRecyclerView = detailblogRecyclerView
        val layoutManager = LinearLayoutManager(this)
        detailElemRecyclerView.layoutManager = layoutManager
        val adapter = DetailAdapter(ListDetail)
        detailElemRecyclerView.adapter = adapter

        /********************点击点赞或评论重新装配*******************/
        adapter.setOnItemClickListener(object : DetailAdapter.OnItemClickListener {
            override fun onclick(v: View, position: Int) {
                val thumbUpAdapter = ThumbUpAdapter(ThumbUpDetail)
                commentElemRecyclerView.adapter = thumbUpAdapter
            }

            override fun onclick2(v: View, position: Int) {
                val commentadapter = CommentAdapter(CommentDetail)
                commentElemRecyclerView.adapter = commentadapter
            }
        })

        /********************点击下方评论弹出评论框*******************/
        binding.tvComment.setOnClickListener {
            showCommentDialog()
        }
        /********************点击下方转发弹出转发框*******************/
        binding.tvrelay.setOnClickListener{
            showRelayDialog()
        }

        /**********************点击下方赞进行点赞********************/
        binding.Agree.setOnClickListener {
            if(temp_agree == 1) {
                commentRecyclerView.adapter?.notifyDataSetChanged()
                ListDetail[0].thumbUp = ListDetail[0].thumbUp - 1
                temp_agree = -1
                binding.Agree.setTextColor(Color.rgb(98, 98, 98))
                Agree()
            }
            else{
                commentRecyclerView.adapter?.notifyDataSetChanged()
                ListDetail[0].thumbUp = ListDetail[0].thumbUp + 1
                temp_agree = 1
                binding.Agree.setTextColor(Color.rgb(255, 0, 0))
                DisAgree()
            }
            load_thumbUp()
        }
    }
    /********************弹出评论框的方法*******************/
    private fun showCommentDialog() {
        val dialog = BottomSheetDialog(this,R.style.BottomSheetEdit)
        var commentView = LayoutInflater.from(this).inflate(R.layout.comment_dialog_layout, null)
        val commentText = commentView.findViewById(R.id.dialog_comment_et) as EditText
        val bt_comment = commentView.findViewById(R.id.dialog_comment_bt) as Button
        dialog.setContentView(commentView)
        //解决bsd显示不全的情况
        var parent = commentView.getParent() as View
        var behavior: BottomSheetBehavior<*> = BottomSheetBehavior.from(parent)
        commentView.measure(0,0);
        behavior.setPeekHeight(commentView.getMeasuredHeight());
        bt_comment.setOnClickListener {

        }
        commentText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                if (!TextUtils.isEmpty(charSequence) && charSequence.length > 2) {
                    bt_comment.setBackgroundColor(Color.parseColor("#FFB568"))
                } else {
                    bt_comment.setBackgroundColor(Color.parseColor("#D8D8D8"))
                }
            }
            override fun afterTextChanged(editable: Editable) {}
        })
        dialog.show()
    }
    /********************弹出转发页面的方法*******************/
    @SuppressLint("MissingInflatedId")
    private fun showRelayDialog() {
        val dialog = BottomSheetDialog(this,R.style.BottomSheetEdit)
        var relayView = LayoutInflater.from(this).inflate(R.layout.relay_dialog_layout, null)
        val relayText = relayView.findViewById(R.id.dialog_relay_et) as EditText
        val bt_relay = relayView.findViewById(R.id.dialog_relay_bt) as Button
        val cancel = relayView.findViewById(R.id.tvcancel) as TextView
        dialog.setContentView(relayView)
        //解决bsd显示不全的情况
        var parent = relayView.getParent() as View
        var behavior: BottomSheetBehavior<*> = BottomSheetBehavior.from(parent)
        relayView.measure(0,0);
        behavior.setPeekHeight(relayView.getMeasuredHeight())
        bt_relay.setOnClickListener {

        }
        cancel.setOnClickListener{

        }
        relayText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                if (!TextUtils.isEmpty(charSequence) && charSequence.length > 2) {
                    bt_relay.setBackgroundColor(Color.parseColor("#FFB568"))
                } else {
                    bt_relay.setBackgroundColor(Color.parseColor("#D8D8D8"))
                }
            }
            override fun afterTextChanged(editable: Editable) {}
        })
        dialog.show()
    }
    /**********************根据图片名获取id*********************/
    private fun getResourceId(imageName: String): Int {
        val ctx = baseContext
        val resId = resources.getIdentifier(imageName, "drawable", ctx.packageName)
        return resId;
    }

    /**********************load函数加载comment********************/
    @SuppressLint("NotifyDataSetChanged")
    private fun load_comment() {
        thread {
            try {
                var server_url =
                    "http://${server_ip}:8081/WeBlog_war_exploded/Comment_find.jsp"
                val client = OkHttpClient()
                val requestBody = FormBody.Builder()
                    .add("blog_id", blog_id.toString())  //main_user_id.toString())
                    .build()
                val request = Request.Builder()
                    .url(server_url)
                    .post(requestBody)
                    .build()
                val response = client.newCall(request).execute()
                val responseData = response.body?.string()
                if (responseData != null) {
                    runOnUiThread {
                        parseJsonComment(responseData.trim())
                    }
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun parseJsonComment(jsonStr:String){
        CommentDetail.clear()
        commentRecyclerView.adapter?.notifyDataSetChanged()
        try{
            CommentDetail.clear()
            val jsonArray = JSONArray(jsonStr)
            commentRecyclerView.adapter?.notifyDataSetChanged()
            for (i in 0 until jsonArray.length()){
                val jsonObject = jsonArray.getJSONObject(i)
                val id = jsonObject.getInt("id")
                val user_id = jsonObject.getInt("user_id")
                val image = jsonObject.getString("head")
                val name = jsonObject.getString("name")
                val time = jsonObject.getString("time")
                val place = jsonObject.getString("place")
                val text = jsonObject.getString("text")
                val elemInfo = comment_elem(id,user_id,getResourceId(image),name,time,place,text)
                CommentDetail.add(elemInfo)
            }
        }
        catch(e:Exception){
            e.printStackTrace()
        }
    }

    /**********************load函数加载thumbUp********************/
    private fun load_thumbUp() {
        thread {
            try {
                var server_url =
                    "http://${server_ip}:8081/WeBlog_war_exploded/Agree_find.jsp"
                val client = OkHttpClient()
                val requestBody = FormBody.Builder()
                    .add("blog_id", blog_id.toString())
                    .build()
                val request = Request.Builder()
                    .url(server_url)
                    .post(requestBody)
                    .build()
                val response = client.newCall(request).execute()
                val responseData = response.body?.string()
                if (responseData != null) {
                    runOnUiThread {
                        parseJsonAgree(responseData.trim())
                    }
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun parseJsonAgree(jsonStr:String){
        ThumbUpDetail.clear()
        commentRecyclerView.adapter?.notifyDataSetChanged()
        try{
            val jsonArray = JSONArray(jsonStr)
            commentRecyclerView.adapter?.notifyDataSetChanged()
            for (i in 0 until jsonArray.length()){
                val jsonObject = jsonArray.getJSONObject(i)
                val id = jsonObject.getInt("id")
                val user_id = jsonObject.getInt("user_id")
                val image = jsonObject.getString("head")
                val name = jsonObject.getString("name")
                val text = jsonObject.getString("text")
                val elemInfo = thumbUp_elem(id,user_id,getResourceId(image),name,text)
                ThumbUpDetail.add(elemInfo)
            }
        }
        catch(e:Exception){
            e.printStackTrace()
        }
    }

    /**********************右下角点赞操作********************/
    @SuppressLint("NotifyDataSetChanged")
    private fun Agree() {
        detailblogRecyclerView.adapter?.notifyDataSetChanged()
        commentRecyclerView.adapter?.notifyDataSetChanged()
        thread {
            try {
                var server_url =
                    "http://${server_ip}:8081/WeBlog_war_exploded/Agree_insert.jsp"
                val client = OkHttpClient()
                val requestBody = FormBody.Builder()
                    .add("id", blog_id.toString())
                    .add("user_id", blog_user_id.toString())
                    .add("thumbup", ListDetail[0].thumbUp.toString())
                    .add("text", URLEncoder.encode("说的很有道理","UTF-8"))
                    //.add("text", URLEncoder.encode(ListDetail[0].Text,"UTF-8"))
                    .build()
                val request = Request.Builder()
                    .url(server_url)
                    .post(requestBody)
                    .build()
                val response = client.newCall(request).execute()
                val responseData = response.body?.string()
                if (responseData != null) {
                    runOnUiThread {
                    }
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }
    /**********************右下角取消点赞操作********************/
    @SuppressLint("NotifyDataSetChanged")
    private fun DisAgree() {
        detailblogRecyclerView.adapter?.notifyDataSetChanged()
        commentRecyclerView.adapter?.notifyDataSetChanged()
        thread {
            try {
                var server_url =
                    "http://${server_ip}:8081/WeBlog_war_exploded/Agree_delete.jsp"
                val client = OkHttpClient()
                val requestBody = FormBody.Builder()
                    .add("id", blog_id.toString())
                    .add("user_id", blog_user_id.toString())
                    .add("thumbup", ListDetail[0].thumbUp.toString())
                    .add("text", URLEncoder.encode("说的很有道理","UTF-8"))
                    .build()
                val request = Request.Builder()
                    .url(server_url)
                    .post(requestBody)
                    .build()
                val response = client.newCall(request).execute()
                val responseData = response.body?.string()
                if (responseData != null) {
                    runOnUiThread {
                    }
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }
    /**********************详情页面的Holder**********************/
    private class DetailViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.tvPicture)
        val name: TextView = view.findViewById(R.id.tvName)
        val time: TextView = view.findViewById(R.id.tvTime)
        val place: TextView = view.findViewById(R.id.tvPlace)
        val text: TextView = view.findViewById(R.id.tvText)
        val relay: TextView = view.findViewById(R.id.tvRelay)
        val comment: TextView = view.findViewById(R.id.tvComment)
        val thumbup: TextView = view.findViewById(R.id.tvThumbup)
    }

    /**********************评论的Holder**********************/
    private class CommentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image2: ImageView = view.findViewById(R.id.tvPicture2)
        val name2: TextView = view.findViewById(R.id.tvName2)
        val time2: TextView = view.findViewById(R.id.tvTime2)
        val place2: TextView = view.findViewById(R.id.tvPlace2)
        val text2: TextView = view.findViewById(R.id.tvText2)
    }

    /**********************点赞的Holder**********************/
    private class ThumbUpViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image3: ImageView = view.findViewById(R.id.tvPicture3)
        val name3: TextView = view.findViewById(R.id.tvName3)
        val text3: TextView = view.findViewById(R.id.tvText3)
    }

    /**********************详情页面适配器************************/
    private class DetailAdapter(val ListDetail: List<main_elem_Info>) :
        RecyclerView.Adapter<DetailViewHolder>() {
        interface OnItemClickListener {
            fun onclick(v: View, position: Int)
            fun onclick2(v: View, position: Int)
        }

        private var onItemClickListener: OnItemClickListener? = null
        fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
            this.onItemClickListener = onItemClickListener
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.detail_elem, parent, false)
            return DetailViewHolder(view)
        }

        override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
            val info = ListDetail[position]
            holder.image.setImageResource(info.Image)
            holder.name.text = info.name
            holder.time.text = info.time
            holder.place.text = info.place
            holder.text.text = info.Text
            holder.relay.text = info.relay.toString()
            holder.comment.text = info.comment.toString()
            holder.thumbup.text = info.thumbUp.toString()
            if (onItemClickListener != null) {
                holder.itemView.tvThumbup.setOnClickListener {
                    onItemClickListener?.onclick(
                        holder.itemView.tvThumbup,
                        position
                    )
                }
            }
            if (onItemClickListener != null) {
                holder.itemView.tvComment.setOnClickListener {
                    onItemClickListener?.onclick2(
                        holder.itemView.tvComment,
                        position
                    )
                }
            }
        }

        override fun getItemCount() = ListDetail.size
    }

    /**********************评论的适配器************************/
    private class CommentAdapter(val CommentDetail: List<comment_elem>) :
        RecyclerView.Adapter<CommentViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.comment_elem, parent, false)
            return CommentViewHolder(view)
        }

        override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
            val commentinfo = CommentDetail[position]
            holder.image2.setImageResource(commentinfo.Image)
            holder.name2.text = commentinfo.name
            holder.time2.text = commentinfo.time
            holder.place2.text = commentinfo.place
            holder.text2.text = commentinfo.Text
        }

        override fun getItemCount() = CommentDetail.size
    }

    /**********************点赞的适配器************************/
    private class ThumbUpAdapter(val ThumbUptDetail: List<thumbUp_elem>) :
        RecyclerView.Adapter<ThumbUpViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThumbUpViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.agree_elem, parent, false)
            return ThumbUpViewHolder(view)
        }

        override fun onBindViewHolder(holder: ThumbUpViewHolder, position: Int) {
            val thumbuptinfo = ThumbUptDetail[position]
            holder.image3.setImageResource(thumbuptinfo.Image)
            holder.name3.text = thumbuptinfo.name
            holder.text3.text = thumbuptinfo.Text
        }

        override fun getItemCount() = ThumbUptDetail.size
    }

}