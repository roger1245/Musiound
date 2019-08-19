package com.rg.musiound.view.activity

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rg.musiound.R
import com.rg.musiound.base.BaseActivity
import com.rg.musiound.bean.SongList
import com.rg.musiound.interfaces.model.IActivitySongListModel
import com.rg.musiound.interfaces.presenter.IActivitySongListPresenter
import com.rg.musiound.interfaces.view.IActivitySongListView
import com.rg.musiound.presenter.ActivitySongListPresenter
import com.rg.musiound.util.OnItemClickListener
import com.rg.musiound.util.extensions.dp2px
import com.rg.musiound.view.adapter.SongListAdapter
import org.jetbrains.anko.find

class SongListActivity : BaseActivity<IActivitySongListView, IActivitySongListPresenter, IActivitySongListModel>(), IActivitySongListView {
    private lateinit var recyclerView: RecyclerView

    override fun setSongList(songList: List<SongList>) {
        val adapter = SongListAdapter(songList, this)
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                val id = songList[position - 1].id
                val intent = Intent(this@SongListActivity, ListDetailActivity::class.java)
                intent.putExtra("id", id)
                startActivity(intent)
            }

        })
        recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                super.getItemOffsets(outRect, view, parent, state)
                outRect.top = dp2px(25)
                outRect.left = dp2px(7)
                outRect.right = dp2px(7)
                outRect.bottom = dp2px(10)
            }
        })
    }

    override fun showError() {
    }

    override fun getViewToAttach(): IActivitySongListView {
        return this
    }

    override fun createPresenter(): IActivitySongListPresenter {
        return ActivitySongListPresenter()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_list)
        recyclerView = find(R.id.rv_activity_song_list)
        val grid = GridLayoutManager(this, 3)
        grid.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                if (position == 0) {
                    return 3
                } else {
                    return 1
                }
            }
        }
        recyclerView.layoutManager = grid
        presenter?.start()
        initToolbar()

    }

    private fun initToolbar() {
        common_toolbar.init(
            title = "歌单广场",
            listener = View.OnClickListener { finish() }
        )
    }
}
