package com.rg.musiound.view.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rg.musiound.R
import com.rg.musiound.base.BaseFragment
import com.rg.musiound.bean.MVListRaw
import com.rg.musiound.bean.SongList
import com.rg.musiound.bean.Subscriber
import com.rg.musiound.interfaces.generic.IActivityGenericModel
import com.rg.musiound.interfaces.generic.IActivityGenericPresenter
import com.rg.musiound.interfaces.generic.IActivityGenericView
import com.rg.musiound.presenter.FragmentMusicRankPresenter
import com.rg.musiound.util.OnItemClickListener
import com.rg.musiound.view.activity.MVPlayActivity
import com.rg.musiound.view.adapter.MVListAdapter
import kotlinx.android.synthetic.main.fragment_music_rank.*
import kotlinx.android.synthetic.main.fragment_search_song_list.*

/**
 * Create by roger
 * on 2019/8/18
 */
class MusicRankFragment :
    BaseFragment<IActivityGenericView<MVListRaw, String>, IActivityGenericPresenter<MVListRaw, String>, IActivityGenericModel<MVListRaw, String>>(),
    IActivityGenericView<MVListRaw, String> {
    override fun onCreateView(view: View, savedInstanceState: Bundle?) {
        presenter?.loadMoreData("", 0)

    }

    private lateinit var adapter: MVListAdapter

    override fun setData(data: MVListRaw, page: Int) {

        adapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                val id = adapter.list[position].id
                val intent = Intent(activity, MVPlayActivity::class.java)
                intent.putExtra("id", id)
                startActivity(intent)
            }

        })

        adapter.list.let {
            if (page == 0 && it.isNotEmpty()) {
                it.clear()
            }
            srl_music_rank.isRefreshing = false
        }
        adapter.list.addAll(data.data)
        adapter.page = page
        adapter.let {
            if (page == 0) {
                it.notifyDataSetChanged()
            } else {
                it.notifyItemRangeInserted(it.getCount() - data.data.size, data.data.size)
            }

        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_music_rank
    }

    override fun getViewToAttach(): IActivityGenericView<MVListRaw, String> {
        return this
    }

    override fun createPresenter(): IActivityGenericPresenter<MVListRaw, String> {
        return FragmentMusicRankPresenter()
    }

    override fun showError() {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    private fun initListener() {
        adapter = MVListAdapter(activity as Context)
        rv_fragment_mv_rank.layoutManager = LinearLayoutManager(activity)
        rv_fragment_mv_rank.adapter = adapter
//        adapter.setOnItemClickListener(object : OnItemClickListener {
//            override fun onItemClick(position: Int) {
//                val id = adapter.list[position - 1].id
//                val intent = Intent(activity, ListDetailActivity::class.java)
//                intent.putExtra("id", id)
//                startActivity(intent)
//            }
//
//        })
        rv_fragment_mv_rank.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val lastPosition = layoutManager.findLastVisibleItemPosition()
                    if (adapter.itemCount - 1 == lastPosition && adapter.isLoadingMore) {
                        presenter?.loadMoreData("", ++adapter.page)

                    }
                }
            }
        })
        srl_music_rank.setOnRefreshListener {
            presenter?.loadMoreData("", 0)
        }
//        rv_fragment_search_song_list.addItemDecoration(object : RecyclerView.ItemDecoration() {
//            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
//                super.getItemOffsets(outRect, view, parent, state)
//                outRect.top = dp2px(6)
//                outRect.left = dp2px(10)
//                outRect.right = dp2px(10)
//                outRect.bottom = dp2px(6)
//            }
//        })

    }
}