package com.rg.musiound.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.rg.musiound.R
import com.rg.musiound.base.BaseActivity
import com.rg.musiound.bean.ListDetailRaw
import com.rg.musiound.bean.Song
import com.rg.musiound.interfaces.model.IActivityListDetailModel
import com.rg.musiound.interfaces.presenter.IActivityListDetailPresenter
import com.rg.musiound.interfaces.view.IActivityListDetailView
import com.rg.musiound.presenter.ActivityListDetailPresenter
import com.rg.musiound.service.PlayManager
import com.rg.musiound.util.OnItemClickListener
import com.rg.musiound.util.extensions.setImageFromUrl
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

    override fun setSongs(songs: ListDetailRaw) {
        val adapter = ListDetailAdapter(this)
        adapter.list.addAll(songs.data.tracks)
        cover.setImageFromUrl(songs.data.coverImgUrl)
        titleString = songs.data.name
        title.text = songs.data.name
        description.text = songs.data.description
        adapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                val track = songs.data.tracks[position]
                val song: Song = Song(track.name, "https://v1.itooi.cn/netease/url?id=${track.id}&quality=flac", track.album.picUrl, track.artists )
                val listSong = songs.data.tracks.map {
                    Song(it.name, "https://v1.itooi.cn/netease/url?id=${it.id}&quality=flac", it.album.picUrl, it.artists )
                }
                PlayManager.getInstance(this@ListDetailActivity).add(listSong)
                PlayManager.getInstance(this@ListDetailActivity).dispatch(song)
                val intent = Intent(this@ListDetailActivity, PlayDetailActivity::class.java)
                startActivity(intent)
            }
        })
        recyclerView.adapter = adapter

    }

    override fun showError() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
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
        toolbar.init(
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
