package com.rg.musiound.db

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.rg.musiound.BaseApp

/**
 * Create by roger
 * on 2019/8/20
 */
const val DATABASE_NAME  = "musiound.db"
class MusicDB : SQLiteOpenHelper(
    BaseApp.context,
    DATABASE_NAME,
    null,
    1
) {

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.let {
            PlayingSong.instance.onCreate(db)
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.let {
            PlayingSong.instance.onCreate(db)
        }
    }


    companion object {
        val instance: MusicDB by lazy { MusicDB() }
    }

}