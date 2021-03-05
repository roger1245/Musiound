package com.rg.musiound.view.activity

import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.githang.statusbar.StatusBarCompat
import com.rg.musiound.BaseApp
import com.rg.musiound.R
import com.rg.musiound.bean.SongList
import com.rg.musiound.db.CSList
import com.rg.musiound.db.CollectSongList
import com.rg.musiound.util.OnItemClickListener
import com.rg.musiound.util.extensions.dp2px
import com.rg.musiound.view.adapter.CSLAdapter
import com.rg.musiound.view.songlistdetail.ListDetailActivity
import kotlinx.android.synthetic.main.fragment_search_song_list.*
import org.jetbrains.anko.find

class CSLActivity : com.rg.musiound.view.BaseActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CSLAdapter

    private fun setCSL(csl: List<CSList>) {
        val list: MutableList<SongList> = mutableListOf()
        adapter.list.addAll(csl)
        adapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                val s = adapter.list[position]
                val intent = Intent(this@CSLActivity, ListDetailActivity::class.java)
                intent.putExtra("id", s.id)
                startActivity(intent)

            }
        })


    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkIsDay()
        setContentView(R.layout.activity_csl)

        initToolbar()
        recyclerView = find(R.id.rv_csl)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        adapter = CSLAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                super.getItemOffsets(outRect, view, parent, state)
                outRect.top = dp2px(6)
                outRect.left = dp2px(10)
                outRect.right = dp2px(10)
                outRect.bottom = dp2px(6)
            }
        })

//        setSongs(CollectSong.instance.getCollectSong())
        setCSL(CollectSongList.instance.getCSL())
    }

    private fun initToolbar() {
        common_toolbar.init(
            title = "收藏的歌单",
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
