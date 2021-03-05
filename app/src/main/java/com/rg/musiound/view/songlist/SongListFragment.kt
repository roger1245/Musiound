package com.rg.musiound.view.songlist

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rg.musiound.R
import com.rg.musiound.base.BaseFragment
import com.rg.musiound.bean.songlist.SongList
import com.rg.musiound.db.CSList
import com.rg.musiound.interfaces.model.IFragmentSongListModel
import com.rg.musiound.interfaces.presenter.IFragmentSongListPresenter
import com.rg.musiound.interfaces.view.IFragmentSongListView
import com.rg.musiound.service.ruler.Rulers
import com.rg.musiound.util.OnItemClickListener
import com.rg.musiound.util.extensions.dp2px
import com.rg.musiound.view.songlistdetail.ListDetailActivity
import kotlinx.android.synthetic.main.fragment_song_list.*
import org.jetbrains.anko.find

/**
 * Create by roger
 * on 2019/8/19
 */
class SongListFragment : IFragmentSongListView<SongList>,
    BaseFragment<IFragmentSongListView<SongList>, IFragmentSongListPresenter<SongList>, IFragmentSongListModel<SongList>>() {


    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SongListAdapter
    private lateinit var category: String
    override fun showError() {

    }


    override fun onLoadMoreData(t: SongList, page: Int) {
        adapter.list.let {
            if (page == 0 && it.isNotEmpty()) {
                it.clear()
            }
            srl_song_list.isRefreshing = false

        }
        adapter.list.addAll(t.playlists)
//        adapter.page = page
//        adapter.isLoadingMore = t.data.more

        if (page == 0) {
            adapter.notifyDataSetChanged()
        } else {
            adapter.notifyItemRangeInserted(adapter.itemCount - 1 - t.playlists.size, t.playlists.size - 1)
        }


//        if (page == 0) {
//            recyclerView.scrollToPosition(0)
//        }

        activity?.let {
            adapter.setOnItemClickListener(object : OnItemClickListener {
                override fun onItemClick(position: Int) {
                    val item = adapter.list[position]
                    Rulers.mCSL = CSList(item.name, item.id.toLong(), "")
                    val id = adapter.list[position - 1].id
                    val intent = Intent(activity, ListDetailActivity::class.java)
                    intent.putExtra("id", id)
                    startActivity(intent)
                }
            })
        }
    }

    override fun onCreateView(view: View, savedInstanceState: Bundle?) {
        category = arguments?.getString("category") as String
        category.let {
            presenter?.loadMoreData(0, it)
        }
        recyclerView = view.find(R.id.rv_fragment_song_list)
        val grid = GridLayoutManager(activity, 3)
        grid.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                if (position == 0) {
                    return 3
                } else if (position == adapter.itemCount - 1) {
                    return 3
                } else {
                    return 1
                }
            }
        }
        recyclerView.layoutManager = grid
        recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                super.getItemOffsets(outRect, view, parent, state)
                outRect.top = dp2px(25)
                outRect.left = dp2px(7)
                outRect.right = dp2px(7)
                outRect.bottom = dp2px(10)
            }
        })
        init()
//        initSwipe()
    }

    private fun init() {
        adapter = SongListAdapter(activity as Context)
        recyclerView.adapter = adapter
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val layoutManager = recyclerView.layoutManager as GridLayoutManager
                    val lastPosition = layoutManager.findLastVisibleItemPosition()
                    if (adapter.itemCount - 1 == lastPosition && adapter.isLoadingMore) {
                        id.let {
                            adapter.page += 1
//                            Log.d("roger", category +"page = ${adapter.page}")

                            presenter?.loadMoreData(adapter.page, category)
                        }
                    }
                }
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSwipe()
    }

    private fun initSwipe() {
        srl_song_list.setOnRefreshListener {
            presenter?.loadMoreData(0, category)
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_song_list
    }

    override fun getViewToAttach(): IFragmentSongListView<SongList> {
        return this

    }

    override fun createPresenter(): IFragmentSongListPresenter<SongList> {
        return FragmentSongListPresenter()
    }
}