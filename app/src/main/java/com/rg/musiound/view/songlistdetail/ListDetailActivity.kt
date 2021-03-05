package com.rg.musiound.view.songlistdetail

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.githang.statusbar.StatusBarCompat
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.rg.musiound.BaseApp
import com.rg.musiound.R
import com.rg.musiound.base.BaseActivity
import com.rg.musiound.bean.Song
import com.rg.musiound.bean.songlistdetail.SongListDetailRoot
import com.rg.musiound.interfaces.model.IActivityListDetailModel
import com.rg.musiound.interfaces.presenter.IActivityListDetailPresenter
import com.rg.musiound.interfaces.view.IActivityListDetailView
import com.rg.musiound.service.PlayManager
import com.rg.musiound.util.OnItemClickListener
import com.rg.musiound.util.extensions.setImageFromUrl
import com.rg.musiound.view.activity.PlayDetailActivity
import com.rg.musiound.view.adapter.ListDetailAdapter
import org.jetbrains.anko.find

class ListDetailActivity : BaseActivity<IActivityListDetailView, IActivityListDetailPresenter, IActivityListDetailModel>(), IActivityListDetailView {
    private lateinit var recyclerView: RecyclerView
    private lateinit var cover: ImageView
    private lateinit var title: TextView
    private lateinit var description: TextView
    private lateinit var toolbar: Toolbar
    private lateinit var appBarLayout: AppBarLayout
    private lateinit var collapsingLayout: CollapsingToolbarLayout
    private lateinit var blur: ImageView
    private lateinit var state: Collapse
    private var titleString: String = "歌单"
    private val songStr = "歌单"
    private lateinit var titleToolbar: TextView

    override fun getViewToAttach(): IActivityListDetailView {
        return this
    }

    override fun createPresenter(): IActivityListDetailPresenter {
        return ActivityListDetailPresenter()
    }

    override fun setSongs(songs: SongListDetailRoot) {
        val adapter = ListDetailAdapter(this)
        adapter.list.addAll(songs.playlist.tracks)
        cover.setImageFromUrl(songs.playlist.coverImgUrl)
        titleString = songs.playlist.name
        title.text = songs.playlist.name
        description.text = songs.playlist.description
        adapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                val track = songs.playlist.tracks[position]
                val song: Song = Song(track.name, track.id.toString(), track.al.picUrl, track.ar )
                val listSong = songs.playlist.tracks.map {
                    Song(it.name, it.id.toString(), it.al.picUrl, it.ar )
                }
                PlayManager.instance.deleteAll()
                PlayManager.instance.add(listSong)
                PlayManager.instance.dispatch(song)


//                val track = songs.playlist.tracks[position]
//
//                songPlayViewModel.getSongDetail(track.id)
//                songPlayViewModel.songDetailRoot.observe(this@ListDetailActivity, {
//                    val song: Song = Song(track.name, it.data[0].url, track.al.picUrl, track.ar )
////                    val listSong = songs.playlist.tracks.map {
////                        Song(it.name, it.id, it.al.picUrl, it.ar )
////                    }
//                    PlayManager.instance.deleteAll()
//                    PlayManager.instance.add(listOf(song))
//                    PlayManager.instance.dispatch(song)
//                })
                val intent = Intent(this@ListDetailActivity, PlayDetailActivity::class.java)
                startActivity(intent)
            }
        })
        recyclerView.adapter = adapter


    }


    override fun showError() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        StatusBarCompat.setStatusBarColor(this, Color.parseColor(BaseApp.context.getString(R.color.accent_always_black as Int)))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_detail)

        recyclerView = find(R.id.rv_activity_list_detail)
        cover = find(R.id.iv_activity_list_detail_cover)
        title = find(R.id.tv_list_detail_title)
        description = find(R.id.tv_list_detail_description)
        toolbar = find(R.id.toolbar_common)
        collapsingLayout = find(R.id.ct_list_detail)
        appBarLayout = find(R.id.ab_list_detail)
        titleToolbar = find(R.id.tv_list_detail_title_toolbar)
        state  = Collapse.UNINITIAL
        setToolbarListener()
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager as RecyclerView.LayoutManager?
        val id = intent.extras?.getLong("id")
        id?.let {
            presenter?.start(it)
        }
        initToolbar()
    }

    private fun initToolbar() {
        toolbar.initBlack(
            title = "",
            listener = View.OnClickListener { finish() }
        )
    }

    private fun setToolbarListener() {
        collapsingLayout.isTitleEnabled = false
        appBarLayout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            override fun onOffsetChanged(p0: AppBarLayout?, p1: Int) {
                if (p1 == 0 && state != Collapse.EXPANDED) {
                    state = Collapse.EXPANDED
                    titleToolbar.text = songStr
                } else if (Math.abs(p1) >= appBarLayout.getTotalScrollRange() && state != Collapse.COLLAPSED) {
                    state = Collapse.COLLAPSED
                    titleToolbar.text = titleString
                } else if (state != Collapse.INTERNEDIATE) {
                    state = Collapse.INTERNEDIATE
                    titleToolbar.text = songStr


                }
            }

        })
    }
    enum class Collapse() {
        EXPANDED,
        COLLAPSED,
        INTERNEDIATE,
        UNINITIAL
    }
}
