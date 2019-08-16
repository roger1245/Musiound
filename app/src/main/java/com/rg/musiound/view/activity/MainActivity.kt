package com.rg.musiound.view.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.rg.musiound.R
import org.jetbrains.anko.find

class MainActivity : AppCompatActivity() {

    private lateinit var play: Button
    private lateinit var songList: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        play = find(R.id.btn_play_detail_activity)
        songList = find(R.id.btn_song_list_activity)
        play.setOnClickListener{
            val intent = Intent(this@MainActivity, PlayDetailActivity::class.java)
            startActivity(intent)
        }
        songList.setOnClickListener{
            val intent = Intent(this@MainActivity, SongListActivity::class.java)
            startActivity(intent)
        }
    }

}
