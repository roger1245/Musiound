package com.rg.musiound.view.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.rg.musiound.R
import com.rg.musiound.view.BaseActivity
import org.jetbrains.anko.find

class MainActivity : BaseActivity() {

    private lateinit var play: Button
    private lateinit var songList: Button
    private lateinit var tabLayout: TabLayout
//    private lateinit var viewPager: ViewPager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        play = find(R.id.btn_play_detail_activity)
        songList = find(R.id.btn_song_list_activity)
        tabLayout = find(R.id.tl_activity_main)
//        viewPager = find(R.id.vp_activity_main)
        play.setOnClickListener {
            val intent = Intent(this@MainActivity, PlayDetailActivity::class.java)
            startActivity(intent)
        }
        songList.setOnClickListener {
            val intent = Intent(this@MainActivity, SongListActivity::class.java)
            startActivity(intent)
        }
    }

}
