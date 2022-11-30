package com.example.weblog.ui.MainActivity

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weblog.R
import com.example.weblog.WeBlogApplication.Companion.context
import com.example.weblog.logic.model.comment_elem
import com.example.weblog.logic.model.main_elem_Info
import com.example.weblog.logic.model.thumbUp_elem
import kotlinx.android.synthetic.main.main_elem.view.*

class MainAdapter(val mainList: List<main_elem_Info>) :
    RecyclerView.Adapter<MainActivity.mainViewHolder>() {
    interface OnItemClickListener {
        fun onclick(v: View, position: Int)
    }
    private var onItemClickListener: OnItemClickListener? = null
    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainActivity.mainViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.main_elem, parent, false)
        return MainActivity.mainViewHolder(view)
    }
    override fun onBindViewHolder(holder: MainActivity.mainViewHolder, position: Int) {
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

internal class CommentAdapter(val CommentDetail: List<comment_elem>) :
    RecyclerView.Adapter<blog_Text.CommentViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): blog_Text.CommentViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.comment_elem, parent, false)
        return blog_Text.CommentViewHolder(view)
    }
    override fun onBindViewHolder(holder: blog_Text.CommentViewHolder, position: Int) {
        val commentinfo = CommentDetail[position]
        holder.image2.setImageResource(commentinfo.Image)
        holder.name2.text = commentinfo.name
        holder.time2.text = commentinfo.time
        holder.place2.text = commentinfo.place
        holder.text2.text = commentinfo.Text
    }
    override fun getItemCount() = CommentDetail.size
}

internal class ThumbUpAdapter(val ThumbUptDetail: List<thumbUp_elem>) :
    RecyclerView.Adapter<blog_Text.ThumbUpViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): blog_Text.ThumbUpViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.agree_elem, parent, false)
        return blog_Text.ThumbUpViewHolder(view)
    }
    override fun onBindViewHolder(holder: blog_Text.ThumbUpViewHolder, position: Int) {
        val thumbuptinfo = ThumbUptDetail[position]
        holder.image3.setImageResource(thumbuptinfo.Image)
        holder.name3.text = thumbuptinfo.name
        holder.text3.text = thumbuptinfo.Text
    }
    override fun getItemCount() = ThumbUptDetail.size
}