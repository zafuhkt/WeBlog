package com.example.weblog.logic.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class main_elem_Info(
    val id:Int,
    val user_id:Int,
    val Image:Int,
    val name:String,
    val time:String,
    val place:String,
    val Text:String,
    val relay:Int,
    val comment:Int,
    var thumbUp:Int
): Parcelable

data class comment_elem(
    val id:Int,
    val user_id:Int,
    val Image:Int,
    val name:String,
    val time:String,
    val place:String,
    val Text:String
)

data class thumbUp_elem(
    val id:Int,
    val user_id:Int,
    val Image:Int,
    val name:String,
    val Text:String
)