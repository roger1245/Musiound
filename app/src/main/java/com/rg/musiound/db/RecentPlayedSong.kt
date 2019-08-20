package com.rg.musiound.db

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.rg.musiound.bean.Artist
import com.rg.musiound.bean.Song

/**
 * Create by roger
 * on 2019/8/20
 */
const val RECENT_NAME = "NAME"
const val RECENT_ARTIST = "ARTIST"
const val RECENT_URL = "URL"
const val RECENT_PIC = "PIC"
const val RECENT_PLAYING_SONG = "PLAYING_SONG"
class RecentPlayedSong {
    companion object {
        val instance: RecentPlayedSong by lazy { RecentPlayedSong() }
    }

    fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS " + RECENT_PLAYING_SONG + "("
                    + RECENT_NAME + " TEXT, "
                    + RECENT_URL + " TEXT,"
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

    fun  addRecentPlayedSong(song: Song) {
        val db = MusicDB.instance.writableDatabase
        db.beginTransaction()

        val ar = song.singer.map { it.name }
        try {
            val values: ContentValues = ContentValues(5)
            values.let {
                it.put(RECENT_NAME, song.name)
                it.put(RECENT_URL, song.url)
                it.put(RECENT_PIC, song.pic)
                val stringBuilder = StringBuilder()
                for (x in ar.withIndex()) {
                    stringBuilder.append(x.value)
                    if (x.index != ar.size - 1) {
                        stringBuilder.append(",")
                    }
                }
                it.put(RECENT_ARTIST, stringBuilder.toString())
            }
            db.insert(RECENT_PLAYING_SONG, null, values)
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
    }
    fun deleteRecentPlayedSong(songs: List<Song>) {
        for (x in songs) {
            deleteRecentPlayedSong(x)
        }
    }
    fun deleteRecentPlayedSong(song: Song) {
        val db = MusicDB.instance.writableDatabase
        db.delete(RECENT_PLAYING_SONG, "${RECENT_URL} = ?", arrayOf(song.url))
    }
    fun deleteAll() {
        val db = MusicDB.instance.writableDatabase
        db.delete(RECENT_PLAYING_SONG, null, null)
    }

    fun getRecentPlayedSong(): MutableList<Song> {
        val list: MutableList<Song> = mutableListOf()
        val db = MusicDB.instance.writableDatabase
        val cursor = db.query(RECENT_PLAYING_SONG,  null, null, null, null, null, null, null)
        if (cursor.moveToFirst()) {
            do {
                val name = cursor.getString(cursor.getColumnIndex(RECENT_NAME))
                val url = cursor.getString(cursor.getColumnIndex(RECENT_URL))
                val pic = cursor.getString(cursor.getColumnIndex(RECENT_PIC))
                val artist = cursor.getString(cursor.getColumnIndex(RECENT_ARTIST))
                val artists = artist.split(",").map {
                    Artist(it)
                }
                list.add(Song(name, url, pic, artists))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }

}

