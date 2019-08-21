package com.rg.musiound.db

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.rg.musiound.bean.Artist
import com.rg.musiound.bean.Song

/**
 * Create by roger
 * on 2019/8/21
 */
const val COLLECT_NAME = "NAME"
const val COLLECT_ARTIST = "ARTIST"
const val COLLECT_URL = "URL"
const val COLLECT_PIC = "PIC"
const val COLLECT_PLAYING_SONG = "COLLECT_PLAYED_SONG"
class CollectSong {
    companion object {
        val instance: CollectSong by lazy { CollectSong() }
    }

    fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS " + COLLECT_PLAYING_SONG + "("
                    + COLLECT_NAME + " TEXT, "
                    + COLLECT_URL + " TEXT,"
                    + COLLECT_PIC + " TEXT, "
                    + COLLECT_ARTIST + " TEXT );"
        )
    }

    fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $COLLECT_PLAYING_SONG")
        onCreate(db)
    }

    fun addAll(songs: List<Song>) {
        for (x in songs) {
            addCollectSong(x)
        }
    }

    fun addCollectSong(song: Song) {
        val db = MusicDB.instance.writableDatabase
        db.beginTransaction()

        val ar = song.singer.map { it.name }
        try {
            val values: ContentValues = ContentValues(5)
            values.let {
                it.put(COLLECT_NAME, song.name)
                it.put(COLLECT_URL, song.url)
                it.put(COLLECT_PIC, song.pic)
                val stringBuilder = StringBuilder()
                for (x in ar.withIndex()) {
                    stringBuilder.append(x.value)
                    if (x.index != ar.size - 1) {
                        stringBuilder.append(",")
                    }
                }
                it.put(COLLECT_ARTIST, stringBuilder.toString())
            }
            db.insert(COLLECT_PLAYING_SONG, null, values)
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
    }

    fun deleteCollectSong(songs: List<Song>) {
        for (x in songs) {
            deleteCollectSong(x)
        }
    }

    fun deleteCollectSong(song: Song) {
        val db = MusicDB.instance.writableDatabase
        db.delete(COLLECT_PLAYING_SONG, "${COLLECT_URL} = ?", arrayOf(song.url))
    }

    fun deleteAll() {
        val db = MusicDB.instance.writableDatabase
        db.delete(COLLECT_PLAYING_SONG, null, null)
    }

    fun getCollectSong(): MutableList<Song> {
        val list: MutableList<Song> = mutableListOf()
        val db = MusicDB.instance.writableDatabase
        val cursor = db.query(COLLECT_PLAYING_SONG, null, null, null, null, null, null, null)
        if (cursor.moveToFirst()) {
            do {
                val name = cursor.getString(cursor.getColumnIndex(COLLECT_NAME))
                val url = cursor.getString(cursor.getColumnIndex(COLLECT_URL))
                val pic = cursor.getString(cursor.getColumnIndex(COLLECT_PIC))
                val artist = cursor.getString(cursor.getColumnIndex(COLLECT_ARTIST))
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