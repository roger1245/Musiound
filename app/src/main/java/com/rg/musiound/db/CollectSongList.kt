package com.rg.musiound.db

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.rg.musiound.bean.SongList

/**
 * Create by roger
 * on 2019/8/23
 */
const val CSL = "CSL"
const val CSL_TITLE = "CSL_TITLE"
const val CSL_ID = "CSL_ID"
const val CSL_PIC = "CSL_PIC"

class CollectSongList {
    companion object {
        val instance: CollectSongList by lazy { CollectSongList() }

    }

    fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS " + CSL + "("
                    + CSL_ID + " LONG, "
                    + CSL_PIC + " TEXT,"
                    + CSL_TITLE + " TEXT );"
        )
    }

    fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $CSL")
        onCreate(db)
    }

    fun addCSL(sl: CSList) {
        if (queryIfExist(sl)) {
            return
        }
        val db = MusicDB.instance.writableDatabase
        db.beginTransaction()
        try {
            val values: ContentValues = ContentValues(3)
            values.let {
                it.put(CSL_ID, sl.id)
                it.put(CSL_PIC, sl.pic)
                it.put(CSL_TITLE, sl.title)

            }
            db.insert(CSL, null, values)
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
    }

    fun deleteCSL(sl: CSList) {
        val db = MusicDB.instance.writableDatabase
        db.delete(CSL, "${CSL_ID} = ?", arrayOf(sl.id.toString()))
    }


    fun getCSL(): MutableList<CSList> {
        val list: MutableList<CSList> = mutableListOf()
        val db = MusicDB.instance.writableDatabase
        val cursor = db.query(CSL, null, null, null, null, null, null, null)
        if (cursor.moveToFirst()) {
            do {
                val title = cursor.getString(cursor.getColumnIndex(CSL_TITLE))
                val pic = cursor.getString(cursor.getColumnIndex(CSL_PIC))
                val id = cursor.getString(cursor.getColumnIndex(CSL_ID))
                list.add(CSList(title, id.toLong(), pic))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }

    fun queryIfExist(sl: CSList) : Boolean {
        val db = MusicDB.instance.writableDatabase
        val cursor = db.query(CSL, null, CSL_ID + " = ?", arrayOf(sl.id.toString()), null, null, null)
        if (cursor.moveToFirst()) {
            cursor.close()
            return true
        } else {
            cursor.close()
            return false
        }
    }
}


data class CSList(
    val title: String,
    val id: Long,
    val pic: String
)