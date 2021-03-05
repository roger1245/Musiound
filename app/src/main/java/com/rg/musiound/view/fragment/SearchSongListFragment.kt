package com.rg.musiound.view.fragment

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rg.musiound.R
import com.rg.musiound.base.BaseFragment
import com.rg.musiound.bean.*
import com.rg.musiound.interfaces.generic.IActivityGenericModel
import com.rg.musiound.interfaces.generic.IActivityGenericPresenter
import com.rg.musiound.interfaces.generic.IActivityGenericView
import com.rg.musiound.presenter.FragmentSongListSearchPresenter
import com.rg.musiound.util.OnItemClickListener
import com.rg.musiound.util.extensions.dp2px
import com.rg.musiound.view.songlistdetail.ListDetailActivity
import com.rg.musiound.view.songlist.SongListAdapter
import com.rg.musiound.view.widget.MessageEvent
import kotlinx.android.synthetic.main.fragment_search_song_list.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


/**
 * Create by roger
 * on 2019/8/19
 */
class SearchSongListFragment :
    BaseFragment<IActivityGenericView<SongListSearchRaw, String>, IActivityGenericPresenter<SongListSearchRaw, String>, IActivityGenericModel<SongListSearchRaw, String>>(),
    IActivityGenericView<SongListSearchRaw, String> {


    private lateinit var adapter: SongListAdapter
    private var uri: String? = null

    override fun onCreateView(view: View, savedInstanceState: Bundle?) {
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_search_song_list
    }

    override fun getViewToAttach(): IActivityGenericView<SongListSearchRaw, String> {
        return this
    }

    override fun createPresenter(): IActivityGenericPresenter<SongListSearchRaw, String> {
        return FragmentSongListSearchPresenter()
    }

    override fun setData(data: SongListSearchRaw, page: Int) {

        val dataMapped = data.data.playlists.map {
            SongList(it.description?:"", it.id, "", it.name, listOf(""), listOf(Subscriber("", "")), it.coverImgUrl)
        }
        adapter.list.let {
            if (page == 0 && it.isNotEmpty()) {
                it.clear()
            }
            srl_search_song_list.isRefreshing = false
        }
//        adapter.list.addAll(dataMapped)
        adapter.page = page
        adapter.let {
            if (page == 0) {
                it.notifyDataSetChanged()
            } else {
                it.notifyItemRangeInserted(it.getCount() - dataMapped.size, dataMapped.size)
            }

        }
    }

    override fun showError() {
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        EventBus.getDefault().register(this)
        initListener()

    }


    override fun onDestroyView() {
        super.onDestroyView()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEditTextMessageEvent(event: MessageEvent) {/* Do something */
        event.getMessage()?.let {
            uri = it
            presenter?.loadMoreData(it, 0)
        }
    }

    private fun initListener() {
        adapter = SongListAdapter(activity as Context)
        adapter.isFromSearch = true
        rv_fragment_search_song_list.layoutManager = LinearLayoutManager(activity)
        rv_fragment_search_song_list.adapter = adapter
        adapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                val item = adapter.list[position - 1]
//                Rulers.mCSL = CSList(item.name, item.id, item.coverImgUrl)
                val id = adapter.list[position - 1].id
                val intent = Intent(activity, ListDetailActivity::class.java)
                intent.putExtra("id", id)
                startActivity(intent)
            }

        })
        rv_fragment_search_song_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val lastPosition = layoutManager.findLastVisibleItemPosition()
                    if (adapter.itemCount - 1 == lastPosition && adapter.isLoadingMore) {
                        uri?.let {
                            presenter?.loadMoreData(it, ++adapter.page)
                        }
                    }
                }
            }
        })
        rv_fragment_search_song_list.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                super.getItemOffsets(outRect, view, parent, state)
                outRect.top = dp2px(6)
                outRect.left = dp2px(10)
                outRect.right = dp2px(10)
                outRect.bottom = dp2px(6)
            }
        })
        srl_search_song_list.setOnRefreshListener {
            uri?.let {
                presenter?.loadMoreData(it, 0)
            }
            if (uri == null) {
                srl_search_song_list.isRefreshing = false
            }
        }
    }

}