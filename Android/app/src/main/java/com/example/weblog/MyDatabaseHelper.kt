package com.example.weblog
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class MyDatabaseHelper(val context:Context,name:String,version:Int):SQLiteOpenHelper(context,name,null,version){
    /*********************每条微博的表******************/
    private val create_mainelem = "create table MainElem (" +
            "id integer primary key autoincrement," +
            "user_id integer," +
            "Image text," +
            "name text," +
            "time text," +
            "place text," +
            "Text text," +
            "relay text," +
            "comment text," +
            "thumbUp text)"

    /*********************微博评论表*******************/
    private val create_elemdetail = "create table ElemDetail (" +
            "id integer primary key," +
            "user_id integer," +
            "Image text," +
            "name text," +
            "time text," +
            "place text," +
            "Text text)"

    /*********************微博点赞表*******************/
    private val create_elemthumbup = "create table ElemThumbUp (" +
            "id integer primary key," +
            "user_id integer," +
            "Image text," +
            "name text," +
            "Text text)"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(create_mainelem)
        db?.execSQL(create_elemdetail)
        db?.execSQL(create_elemthumbup)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("drop table if exists MainElem")
        onCreate(db)
    }
}