package com.shiromoku.newbeeMall.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

open class DatabaseHelper(context: Context, databaseName:String, version: Int) : SQLiteOpenHelper(context,databaseName,null,version) {
    override fun onCreate(db: SQLiteDatabase?) {
        var sql = "create table if not EXISTS goodsImage(" +
                "goodId text not null  primary key ," +
                "imagePath text" +
                ")"
        db!!.execSQL(sql)

        sql =  "create table if not EXISTS keyValue(" +
//                "id integer primary key autoincrement ," +
                "key text not null primary key," +
                "value text" +
                ")"
        db.execSQL(sql)

        sql =  "create table if not EXISTS goodsInfo(" +
                "goodId text not null primary key ," +
                "goodsName text ," +
                "goodsIntro text ," +
                "sellingPrice text" +
                ")"
        db.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}