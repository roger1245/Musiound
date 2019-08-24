package com.rg.musiound.view.activity

import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.githang.statusbar.StatusBarCompat
import com.rg.musiound.BaseApp
import com.rg.musiound.R
import com.rg.musiound.bean.Album
import com.rg.musiound.bean.Song
import com.rg.musiound.bean.Track
import com.rg.musiound.db.CollectSong
import com.rg.musiound.service.PlayManager
import com.rg.musiound.util.OnItemClickListener
import com.rg.musiound.view.adapter.ListDetailAdapter
import org.jetbrains.anko.find

/**
 * Create by roger
 * on 2019/8/21
 */
class CollectActivity : com.rg.musiound.view.BaseActivity() {
    private lateinit var recyclerView: RecyclerView

    private fun setSongs(song: List<Song>) {
        val tracks: MutableList<Track> = mutableListOf()
        for (x in song) {
            tracks.add(Track(1L, x.name, x.singer, Album("", "")))

        }
        val adapter = ListDetailAdapter(this)
        adapter.list.addAll(tracks)
        adapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                val s = song[position]
                PlayManager.instance.deleteAll()
                PlayManager.instance.add(song)
                PlayManager.instance.startAsong(s)
                val intent = Intent(this@CollectActivity, PlayDetailActivity::class.java)
                startActivity(intent)

            }
        })
        recyclerView.adapter = adapter

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkIsDay()
        setContentView(R.layout.activity_recent_played)

        initToolbar()
        recyclerView = find(R.id.rv_activity_recent_played)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        setSongs(CollectSong.instance.getCollectSong())

    }

    private fun initToolbar() {
        common_toolbar.init(
            title = "我的收藏",
            listener = View.OnClickListener { finish() }
        )
    }

    fun checkIsDay() {
        val mode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        if (mode == Configuration.UI_MODE_NIGHT_YES) {
            StatusBarCompat.setStatusBarColor(
                this,
                Color.parseColor(BaseApp.context.getString(R.color.accent_always_black as Int))
            )
        } else if (mode == Configuration.UI_MODE_NIGHT_NO) {
            StatusBarCompat.setStatusBarColor(
                this,
                Color.parseColor(BaseApp.context.getString(R.color.accent_always_white as Int))
            )
        }
    }
}