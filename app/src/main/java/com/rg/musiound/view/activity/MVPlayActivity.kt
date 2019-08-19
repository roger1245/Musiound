package com.rg.musiound.view.activity

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rg.musiound.base.BaseActivity
import com.rg.musiound.bean.MVComment
import com.rg.musiound.bean.MVCommentRaw
import com.rg.musiound.bean.MVDetailRaw
import com.rg.musiound.interfaces.model.IActivityMVPlayerModel
import com.rg.musiound.interfaces.presenter.IActivityMVPlayerPresenter
import com.rg.musiound.interfaces.view.IActivityMVPlayerView
import com.rg.musiound.presenter.ActivityMVPlayerPresenter
import com.rg.musiound.view.adapter.MVCommentAdapter
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.utils.Debuger
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer
import kotlinx.android.synthetic.main.activity_mvplay.*


class MVPlayActivity : IActivityMVPlayerView<MVDetailRaw, MVCommentRaw>,
    BaseActivity<IActivityMVPlayerView<MVDetailRaw, MVCommentRaw>, IActivityMVPlayerPresenter<MVDetailRaw, MVCommentRaw>, IActivityMVPlayerModel<MVDetailRaw, MVCommentRaw>>() {
    private lateinit var adapter: MVCommentAdapter

    override fun onLoadMoreData(t: MVCommentRaw, page: Int) {
        Log.d("roger", "page = $page")
        adapter.comments.addAll(t.data.comments)
        adapter.page = page
        adapter.isLoadingMore = t.data.more
        adapter.comments.let {
            adapter.notifyItemRangeInserted(it.size - t.data.comments.size, t.data.comments.size)
        }
    }

    override fun setMVDetail(t: MVDetailRaw) {
        adapter.detail = t.data.data
    }


    override fun showError() {
    }

    override fun getViewToAttach(): IActivityMVPlayerView<MVDetailRaw, MVCommentRaw> {
        return this
    }

    override fun createPresenter(): IActivityMVPlayerPresenter<MVDetailRaw, MVCommentRaw> {
        return ActivityMVPlayerPresenter()
    }

    private lateinit var orientationUtils: OrientationUtils
    private var isPlay: Boolean = false
    private var isPause: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.rg.musiound.R.layout.activity_mvplay)

        val id = intent.extras?.getLong("id")
        var source: String? = null
        id?.let {
            source = "https://v1.itooi.cn/netease/mvUrl?id=$id&quality=1080"
            presenter?.start(it)
        }
        source?.let { init(it) }
        rv_player_comment.layoutManager = LinearLayoutManager(this)
        adapter = MVCommentAdapter(this)
        rv_player_comment.adapter = adapter
        rv_player_comment.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val lastPosition = layoutManager.findLastVisibleItemPosition()
                    if (adapter.itemCount - 1 == lastPosition && adapter.isLoadingMore) {
                        id?.let {
                            presenter?.loadMoreData(it, ++adapter.page)
                        }
                    }
                }
            }
        })
    }

    private fun init(source: String) {

        orientationUtils = OrientationUtils(this, gsy_player as GSYVideoPlayer)
//初始化不打开外部的旋转
        orientationUtils.isEnable = false

        val gsyVideoOption = GSYVideoOptionBuilder()
        gsyVideoOption
            .setIsTouchWiget(true)
            .setRotateViewAuto(false)
            .setLockLand(false)
            .setAutoFullWithSize(true)
            .setShowFullAnimation(false)
            .setNeedLockFull(true)
            .setUrl(source)
            .setCacheWithPlay(false)
            .setVideoTitle("测试视频")
            .setVideoAllCallBack(object : GSYSampleCallBack() {
                override fun onPrepared(url: String?, vararg objects: Any) {
                    super.onPrepared(url, *objects)
                    //开始播放了才能旋转和全屏
                    orientationUtils.isEnable = true
                    isPlay = true
                }

                override fun onQuitFullscreen(url: String?, vararg objects: Any) {
                    super.onQuitFullscreen(url, *objects)
                    Debuger.printfError("***** onQuitFullscreen **** " + objects!![0])//title
                    Debuger.printfError("***** onQuitFullscreen **** " + objects!![1])//当前非全屏player
                    if (orientationUtils != null) {
                        orientationUtils.backToProtVideo()
                    }
                }
            }).setLockClickListener { view, lock ->
                if (orientationUtils != null) {
                    //配合下方的onConfigurationChanged
                    orientationUtils.isEnable = !lock
                }
            }.build(gsy_player)

        gsy_player.getFullscreenButton().setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                //直接横屏
                orientationUtils.resolveByClick()

                //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                gsy_player.startWindowFullscreen(this@MVPlayActivity, true, true)
            }
        })
    }

    override fun onBackPressed() {
        if (orientationUtils != null) {
            orientationUtils.backToProtVideo()
        }
        if (GSYVideoManager.backFromWindowFull(this)) {
            return
        }
        super.onBackPressed()
    }


    override fun onPause() {
        gsy_player.getCurrentPlayer().onVideoPause()
        super.onPause()
        isPause = true
    }

    override fun onResume() {
        gsy_player.getCurrentPlayer().onVideoResume(false)
        super.onResume()
        isPause = false
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isPlay) {
            gsy_player.getCurrentPlayer().release()
        }
        if (orientationUtils != null)
            orientationUtils.releaseListener()
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        //如果旋转了就全屏
        if (isPlay && !isPause) {
            gsy_player.onConfigurationChanged(this, newConfig, orientationUtils, true, true)
        }
    }
}
