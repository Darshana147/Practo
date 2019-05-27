package com.example.practo.Database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DbSqliteOpenHelper(context:Context,var createTableQuery:String,var dropTableQuery:String):SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        Log.d("table",createTableQuery)
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
       db?.execSQL(dropTableQuery)
        onCreate(db)
    }


    companion object {
        private val DATABASE_NAME = "practo"
        private val DATABASE_VERSION = 2
    }

    fun getReadableDatabaseObject():SQLiteDatabase{
        return readableDatabase
    }

    fun getWritableDatabaseObject():SQLiteDatabase{
        return writableDatabase
    }
}