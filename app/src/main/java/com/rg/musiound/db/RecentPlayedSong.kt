package com.rg.musiound.db

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.rg.musiound.bean.Artist
import com.rg.musiound.bean.Song
import com.rg.musiound.bean.songlistdetail.Ar

/**
 * Create by roger
 * on 2019/8/20
 */
const val RECENT_NAME = "NAME"
const val RECENT_ARTIST = "ARTIST"
const val RECENT_URL = "URL"
const val RECENT_PIC = "PIC"
const val RECENT_PLAYING_SONG = "RECENT_PLAYED_SONG"
const val RECENT_TIME = "TIME"

class RecentPlayedSong {
    companion object {
        val instance: RecentPlayedSong by lazy { RecentPlayedSong() }
    }

    fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS " + RECENT_PLAYING_SONG + "("
                    + RECENT_NAME + " TEXT, "
                    + RECENT_URL + " TEXT,"
                    + RECENT_TIME + " LONG, "
                    + RECENT_PIC + " TEXT, "
                    + RECENT_ARTIST + " TEXT );"
        )
    }

    fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $RECENT_PLAYING_SONG")
        onCreate(db)
    }

    fun addAll(songs: List<Song>) {
        for (x in songs) {
            addRecentPlayedSong(x)
        }
    }


    fun deleteRecentPlayedSong(songs: List<Song>) {
        for (x in songs) {
            deleteRecentPlayedSong(x)
        }
    }

    fun deleteRecentPlayedSong(song: Song) {
        val db = MusicDB.instance.writableDatabase
        db.delete(RECENT_PLAYING_SONG, "${RECENT_URL} = ?", arrayOf(song.id))
    }

    fun deleteAll() {
        val db = MusicDB.instance.writableDatabase
        db.delete(RECENT_PLAYING_SONG, null, null)
    }

    fun getRecentPlayedSong(): MutableList<Song> {
        val list: MutableList<Song> = mutableListOf()
        val db = MusicDB.instance.writableDatabase
        val cursor = db.query(RECENT_PLAYING_SONG, null, null, null, null, null, RECENT_TIME + " DESC", null)
        if (cursor.moveToFirst()) {
            do {
                val name = cursor.getString(cursor.getColumnIndex(RECENT_NAME))
                val url = cursor.getString(cursor.getColumnIndex(RECENT_URL))
                val pic = cursor.getString(cursor.getColumnIndex(RECENT_PIC))
                val artist = cursor.getString(cursor.getColumnIndex(RECENT_ARTIST))
                val artists = artist.split(",").map {
                    val a = Ar()
                    a.name = it
                    a
                }
                list.add(Song(name, url, pic, artists))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }

    //对外接口
    fun addRecentPlayedSong(song: Song) {
        if (queryIfExist(song)) {
            upDate(song)
        } else {
            add(song)
        }
    }

    //基础方法
    private fun queryIfExist(song: Song): Boolean {
        val db = MusicDB.instance.writableDatabase
        val cursor = db.query(RECENT_PLAYING_SONG, null, "$RECENT_URL = ?", arrayOf(song.id), null, null, null)
        if (cursor.moveToFirst()) {
            cursor.close()
            return true
        } else {
            cursor.close()
            return false
        }
    }

    private fun upDate(song: Song) {
        val db = MusicDB.instance.writableDatabase
        db.beginTransaction()

        val ar = song.singer.map { it.name }
        try {
            val values = ContentValues(5)
            values.let {
                it.put(RECENT_NAME, song.name)
                it.put(RECENT_URL, song.id)
                it.put(RECENT_PIC, song.pic)
                val stringBuilder = StringBuilder()
                for (x in ar.withIndex()) {
                    stringBuilder.append(x.value)
                    if (x.index != ar.size - 1) {
                        stringBuilder.append(",")
                    }
                }
                it.put(RECENT_ARTIST, stringBuilder.toString())
                it.put(RECENT_TIME, System.currentTimeMillis())
            }
            db.update(RECENT_PLAYING_SONG, values, "${RECENT_URL} = ?", arrayOf(song.id))
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
    }

    private fun add(song: Song) {
        val db = MusicDB.instance.writableDatabase
        db.beginTransaction()

        val ar = song.singer.map { it.name }
        try {
            val values: ContentValues = ContentValues(5)
            values.let {
                it.put(RECENT_NAME, song.name)
                it.put(RECENT_URL, song.id)
                it.put(RECENT_PIC, song.pic)
                val stringBuilder = StringBuilder()
                for (x in ar.withIndex()) {
                    stringBuilder.append(x.value)
                    if (x.index != ar.size - 1) {
                        stringBuilder.append(",")
                    }
                }
                it.put(RECENT_ARTIST, stringBuilder.toString())
                it.put(RECENT_TIME, System.currentTimeMillis())
            }
            db.insert(RECENT_PLAYING_SONG, null, values)
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
    }

}

