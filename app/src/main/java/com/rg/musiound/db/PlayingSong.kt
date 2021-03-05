package com.rg.musiound.db

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.rg.musiound.bean.Song
import com.rg.musiound.bean.songlistdetail.Ar

/**
 * Create by roger
 * on 2019/8/20
 */
const val NAME = "NAME"
const val ARTIST = "ARTIST"
const val URL = "URL"
const val PIC = "PIC"
const val PLAYING_SONG = "PLAYING_SONG"
class PlayingSong {
    companion object {
        val instance: PlayingSong by lazy { PlayingSong() }
    }

    fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS " + PLAYING_SONG + "("
                    + NAME + " TEXT, "
                    + URL + " TEXT,"
                    + PIC + " TEXT, "
                    + ARTIST + " TEXT );"
        )
    }

    fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $PLAYING_SONG")
        onCreate(db)
    }
    fun addAll(songs: List<Song>) {
        for (x in songs) {
            addPlayingSong(x)
        }
    }

    fun  addPlayingSong(song: Song) {
        val db = MusicDB.instance.writableDatabase
        db.beginTransaction()

        val ar = song.singer.map { it.name }
        try {
            val values: ContentValues = ContentValues(5)
            values.let {
                it.put(NAME, song.name)
                it.put(URL, song.url)
                it.put(PIC, song.pic)
                val stringBuilder = StringBuilder()
                for (x in ar.withIndex()) {
                    stringBuilder.append(x.value)
                    if (x.index != ar.size - 1) {
                        stringBuilder.append(",")
                    }
                }
                it.put(ARTIST, stringBuilder.toString())
            }
            db.insert(PLAYING_SONG, null, values)
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
    }
    fun deletePlayingSong(songs: List<Song>) {
        for (x in songs) {
            deletePlayingSong(x)
        }
    }
    fun deletePlayingSong(song: Song) {
        val db = MusicDB.instance.writableDatabase
        db.delete(PLAYING_SONG, "${URL} = ?", arrayOf(song.url))
    }
    fun deleteAll() {
        val db = MusicDB.instance.writableDatabase
        db.delete(PLAYING_SONG, null, null)
    }

    fun getPlayingSong(): MutableList<Song> {
        val list: MutableList<Song> = mutableListOf()
        val db = MusicDB.instance.writableDatabase
        val cursor = db.query(PLAYING_SONG,  null, null, null, null, null, null, null)
        if (cursor.moveToFirst()) {
            do {
                val name = cursor.getString(cursor.getColumnIndex(NAME))
                val url = cursor.getString(cursor.getColumnIndex(URL))
                val pic = cursor.getString(cursor.getColumnIndex(PIC))
                val artist = cursor.getString(cursor.getColumnIndex(ARTIST))
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

}

