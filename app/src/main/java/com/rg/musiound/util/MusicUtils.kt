package com.rg.musiound.util

import android.content.Context
import android.provider.MediaStore
import android.util.Log
import com.rg.musiound.bean.Song

/**
 * Create by roger
 * on 2019/8/24
 */

fun getMusicLocal(ctx: Context):List<SongLocal> {
    val list: MutableList<SongLocal> = mutableListOf()
    val cursor = ctx.contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
            MediaStore.Audio.AudioColumns.IS_MUSIC)
    if (cursor != null) {
        while (cursor.moveToNext()) {
            var name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME))
            var singer = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
            val path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
            val duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
            val size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
            if (size > 1000 * 800) {
                if (name.contains("-")) {
                    val str = name.split("-")
                    singer = str[0]
                    name = str[1]
                }
            }
            list.add(SongLocal(name, singer, path, duration, size))
        }
        cursor.close()
    }
    Log.d("roger", list.toString())
    return list
}

data class SongLocal(
    val name: String,
    val singer: String,
    val path: String,
    val duration: Int,
    val size: Long
)