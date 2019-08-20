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
import com.rg.musiound.bean.*
import com.rg.musiound.interfaces.generic.IActivityGenericModel
import com.rg.musiound.interfaces.generic.IActivityGenericPresenter
import com.rg.musiound.interfaces.generic.IActivityGenericView
import com.rg.musiound.presenter.FragmentSongSearchPresenter
import com.rg.musiound.service.PlayManager
import com.rg.musiound.util.OnItemClickListener
import com.rg.musiound.view.activity.PlayDetailActivity
import com.rg.musiound.view.adapter.ListDetailAdapter
import com.rg.musiound.view.widget.MessageEvent
import kotlinx.android.synthetic.main.activity_mvplay.*
import kotlinx.android.synthetic.main.fragment_search_song.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Create by roger
 * on 2019/8/19
 */
class SearchSongFragment :
    BaseFragment<IActivityGenericView<SongSearchRaw, String>, IActivityGenericPresenter<SongSearchRaw, String>, IActivityGenericModel<SongSearchRaw, String>>(),
    IActivityGenericView<SongSearchRaw, String> {
    private lateinit var adapter: ListDetailAdapter
    private var uri: String? = null

    override fun onCreateView(view: View, savedInstanceState: Bundle?) {
    }


    override fun getLayoutRes(): Int {
        return R.layout.fragment_search_song
    }

    override fun getViewToAttach(): IActivityGenericView<SongSearchRaw, String> {
        return this
    }

    override fun createPresenter(): IActivityGenericPresenter<SongSearchRaw, String> {
        return FragmentSongSearchPresenter()
    }

    override fun setData(data: SongSearchRaw, page: Int) {
        val dataMapped = data.data.songs.map {
            Track(it.id, it.name, it.ar.map { Artist(it.name) }, Album("", ""))
        }
        adapter.list.let {
            if (page == 0 && it.isNotEmpty()) {
                it.clear()
            }
            srl_search_song.isRefreshing = false
        }
        adapter.list.addAll(dataMapped)
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
        adapter = ListDetailAdapter(activity as Context)
        rv_fragment_search_song.layoutManager = LinearLayoutManager(activity)
        rv_fragment_search_song.adapter = adapter
        adapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                val track = adapter.list[position]
                val song: Song = Song(
                    track.name,
                    "https://v1.itooi.cn/netease/url?id=${track.id}&quality=flac",
                    track.album.picUrl,
                    track.artists
                )
                val listSong = adapter.list.map {
                    Song(
                        it.name,
                        "https://v1.itooi.cn/netease/url?id=${it.id}&quality=flac",
                        it.album.picUrl,
                        it.artists
                    )
                }
                PlayManager.instance.add(listSong)
                PlayManager.instance.dispatch(song)
                val intent = Intent(activity as Context, PlayDetailActivity::class.java)
                startActivity(intent)

            }

        })
        rv_fragment_search_song.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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

        srl_search_song.setOnRefreshListener {
            uri?.let {
                presenter?.loadMoreData(it, 0)
            }
            if (uri == null) {
                srl_search_song.isRefreshing = false
            }
        }
    }
}