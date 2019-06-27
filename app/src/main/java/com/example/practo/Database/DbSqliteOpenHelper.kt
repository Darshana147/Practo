package com.example.practo.Database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.io.*

class DbSqliteOpenHelper(var context:Context,var createTableQuery:String,var dropTableQuery:String):SQLiteOpenHelper(context.applicationContext,DATABASE_NAME,null,DATABASE_VERSION) {

    var DB_PATH = "/data/data/" + context.applicationContext.packageName + "/databases/" + DATABASE_NAME

    init {
        //context.deleteDatabase(DATABASE_NAME)
        checkExists()
    }

    @Throws(IOException::class)
    private fun checkExists() {
        Log.i(TAG, "checkExists()")

        val dbFile = File(DB_PATH)

        if (!dbFile.exists()) {

            Log.i(TAG, "creating database...")

            dbFile.parentFile.mkdirs()
            copyStream(context.assets.open(ASSET_PATH), FileOutputStream(dbFile))
            Log.i(TAG, ASSET_PATH + " has been copied to " + dbFile.absolutePath)
        }
    }

    @Throws(IOException::class)
    private fun copyStream(`is`: InputStream, os: OutputStream) {
        val buf = ByteArray(1024)
        var c = 0
        while (true) {
            c = `is`.read(buf)
            if (c == -1)
                break
            os.write(buf, 0, c)
        }
        `is`.close()
        os.close()
    }


    override fun onCreate(db: SQLiteDatabase?) {
        Log.d("table",createTableQuery)
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
       db?.execSQL(dropTableQuery)
        onCreate(db)
    }


    companion object {
        private val DATABASE_NAME = "practo.db"
        private val DATABASE_VERSION = 2
        private val TAG = "dbOpenHelper"
        private val ASSET_PATH = "practo.db"
    }
}