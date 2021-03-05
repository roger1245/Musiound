package com.rg.musiound.view.activity

import android.Manifest
import android.annotation.SuppressLint
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
import com.rg.musiound.bean.Artist
import com.rg.musiound.bean.Song
import com.rg.musiound.bean.songlistdetail.Ar
import com.rg.musiound.db.CollectSong
import com.rg.musiound.service.PlayManager
import com.rg.musiound.util.OnItemClickListener
import com.rg.musiound.util.SongLocal
import com.rg.musiound.util.getMusicLocal
import com.rg.musiound.view.BaseActivity
import com.rg.musiound.view.adapter.SongLocalAdapter
import com.tbruyelle.rxpermissions2.RxPermissions
import org.jetbrains.anko.find
import org.jetbrains.anko.toast


class SongLocalActivity : BaseActivity() {
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkIsDay()
        setContentView(R.layout.activity_song_local)
        initToolbar()
        recyclerView = find(R.id.rv_activity_song_local)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        request()
    }

    private fun checkIsDay() {
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

    @SuppressLint("CheckResult")
    private fun request() {
        val rxPermissions = RxPermissions(this)
        rxPermissions
            .request(Manifest.permission.READ_EXTERNAL_STORAGE)
            .subscribe { granted ->
                if (granted) { // Always true pre-M
                    val list = getMusicLocal(this)
                    setSongLocal(list)
                } else {
                    toast("请打开读取存储权限")
                }
            }
    }

    private fun initToolbar() {
        common_toolbar.init(
            title = "本地音乐",
            listener = View.OnClickListener { finish() }
        )
    }

    private fun setSongLocal(list: List<SongLocal>) {
        val adapter = SongLocalAdapter(this)
        adapter.list.addAll(list)
        adapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                val songLocal = list[position]
                val a = Ar()
                a.name = songLocal.singer
                val song: Song = Song(songLocal.name, songLocal.path, "", listOf(a) )
                val listSong = list.map {
                    val a = Ar()
                    a.name = songLocal.singer
                    Song(it.name, it.path, "", listOf(a) )

                }
                PlayManager.instance.deleteAll()
                PlayManager.instance.add(listSong)
                PlayManager.instance.dispatch(song)
                val intent = Intent(this@SongLocalActivity, PlayDetailActivity::class.java)
                startActivity(intent)
            }

        })
        recyclerView.adapter = adapter
    }
}
