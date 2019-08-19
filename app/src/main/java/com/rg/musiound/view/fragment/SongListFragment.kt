package com.rg.musiound.view.fragment

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rg.musiound.R
import com.rg.musiound.base.BaseFragment
import com.rg.musiound.bean.SongListRaw
import com.rg.musiound.interfaces.model.IFragmentSongListModel
import com.rg.musiound.interfaces.presenter.IFragmentSongListPresenter
import com.rg.musiound.interfaces.view.IFragmentSongListView
import com.rg.musiound.presenter.FragmentSongListPresenter
import com.rg.musiound.util.OnItemClickListener
import com.rg.musiound.util.extensions.dp2px
import com.rg.musiound.view.activity.ListDetailActivity
import com.rg.musiound.view.adapter.SongListAdapter
import org.jetbrains.anko.find

/**
 * Create by roger
 * on 2019/8/19
 */
class SongListFragment : IFragmentSongListView<SongListRaw>,
    BaseFragment<IFragmentSongListView<SongListRaw>, IFragmentSongListPresenter<SongListRaw>, IFragmentSongListModel<SongListRaw>>() {
    private lateinit var recyclerView: RecyclerView
    override fun showError() {

    }

    override fun onLoadMoreData(t: SongListRaw, page: Int) {

        activity?.let {
            val adapter = SongListAdapter(t.data, it)
            recyclerView.adapter = adapter
            adapter.setOnItemClickListener(object : OnItemClickListener {
                override fun onItemClick(position: Int) {
                    val id = t.data[position - 1].id
                    val intent = Intent(activity, ListDetailActivity::class.java)
                    intent.putExtra("id", id)
                    startActivity(intent)
                }

            })
        }

    }

    override fun onCreateView(view: View, savedInstanceState: Bundle?) {
        val category: String? = arguments?.getString("category")
        category?.let {
            presenter?.loadMoreData(0, it)
        }
        recyclerView = view.find(R.id.rv_fragment_song_list)
        val grid = GridLayoutManager(activity, 3)
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

    override fun getLayoutRes(): Int {
        return R.layout.fragment_song_list
    }

    override fun getViewToAttach(): IFragmentSongListView<SongListRaw> {
        return this

    }

    override fun createPresenter(): IFragmentSongListPresenter<SongListRaw> {
        return FragmentSongListPresenter<SongListRaw>()
    }
}