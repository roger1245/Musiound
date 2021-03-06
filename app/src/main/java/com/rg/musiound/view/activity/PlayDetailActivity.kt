package com.rg.musiound.view.activity

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.githang.statusbar.StatusBarCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.rg.musiound.BaseApp
import com.rg.musiound.R
import com.rg.musiound.bean.Song
import com.rg.musiound.db.CollectSong
import com.rg.musiound.db.CollectSongList
import com.rg.musiound.service.PlayManager
import com.rg.musiound.service.PlayService
import com.rg.musiound.service.ruler.LIST_LOOP
import com.rg.musiound.service.ruler.RANDOM
import com.rg.musiound.service.ruler.Rulers
import com.rg.musiound.service.ruler.SINGLE_LOOP
import com.rg.musiound.util.dp2px
import com.rg.musiound.view.BaseActivity
import com.rg.musiound.view.adapter.DialogBottomAdapter
import com.rg.musiound.view.adapter.OnBottomClickListener
import com.rg.musiound.view.adapter.PlayDetailViewPagerAdapter
import com.rg.musiound.view.fragment.PlayDetailImageFragment
import com.rg.musiound.view.widget.PlayStateChangeEvent
import kotlinx.android.synthetic.main.activity_play_detail.*
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.find
import org.jetbrains.anko.toast


/**
 * Create by roger
 * on 2019/8/16
 */
class PlayDetailActivity : BaseActivity(), PlayManager.Callback, PlayManager.ProgressCallback {
    private var bottom_loop: ImageView? = null
    override fun onPlayListChanged(list: List<Song>) {
        bottomAdapter?.list = list
        bottomAdapter?.notifyDataSetChanged()
        vp.setCurrentItem(Rulers.getCurrentPos(), true)

    }

    override fun onRuleChanged(rule: Int) {
        when (rule) {
            SINGLE_LOOP -> {
                iv_loop?.let { it.setImageResource(R.drawable.activity_play_detail_single_loop) }
                bottom_loop?.let { it.setImageResource(R.drawable.dialog_bottom_song_list_single_loop) }
            }
            LIST_LOOP -> {
                iv_loop?.let { it.setImageResource(R.drawable.activity_play_detail_loop) }
                bottom_loop?.let { it.setImageResource(R.drawable.dialog_bottom_song_list_loop) }


            }
            RANDOM -> {
                iv_loop?.let { it.setImageResource(R.drawable.activity_play_detail_random) }
                bottom_loop?.let { it.setImageResource(R.drawable.dialog_bottom_song_list_random) }
            }
        }
    }


    override fun onPlayListPrepared(songs: List<Song>?) {

    }

    override fun onPlayStateChanged(state: Int, song: Song?) {
        EventBus.getDefault().post(PlayStateChangeEvent(state))
        when (state) {
            PlayService.STATE_INITIALIZED -> {
                song?.let { showSong(song) }
            }
            PlayService.STATE_STARTED -> {
                checkLike()
                vp.setCurrentItem(Rulers.getCurrentPos(), true)
                playIV.setSelected(PlayManager.instance.isPlaying)
                bottomAdapter?.notifyDataSetChanged()
            }
            PlayService.STATE_PAUSED -> {

                playIV.setSelected(PlayManager.instance.isPlaying)
            }

            PlayService.STATE_COMPLETED -> playIV.setSelected(PlayManager.instance.isPlaying)
            PlayService.STATE_STOPPED -> playIV.setSelected(PlayManager.instance.isPlaying)
            PlayService.STATE_RELEASED -> {
                playIV.isSelected = PlayManager.instance.isPlaying
                mSeekBar.progress = 0
            }
            PlayService.STATE_ERROR -> {
                playIV.setSelected(PlayManager.instance.isPlaying)
                mSeekBar.progress = 0
            }
        }
    }


    private fun checkLike() {
        if (getBoolean()) {
            likeIV.setImageResource(R.drawable.activity_play_detail_like_on)
        } else {
            likeIV.setImageResource(R.drawable.activity_play_detail_like_off)
        }
    }

    private fun getBoolean(): Boolean {
        return PlayManager.instance.currentSong?.let {
            CollectSong.instance.queryIfExist(it)
        } ?: false
    }

    override fun onShutdown() {

    }


    override fun onProgress(progress: Int, duration: Int) {
        if (isSeeking) {
            return;
        }
        if (mSeekBar.max != duration) {
            mSeekBar.max = duration

        }
        mSeekBar.progress = progress
    }

    private lateinit var mSeekBar: SeekBar
    private var isSeeking: Boolean = false
    private lateinit var playIV: ImageView
    private lateinit var backIV: ImageView
    private lateinit var forwardIV: ImageView
    private lateinit var songList: RecyclerView
    private lateinit var vp: ViewPager
    private lateinit var likeIV: ImageView
    private var collectSongList: ImageView? = null
    private var bottomAdapter: DialogBottomAdapter? = null
    private lateinit var pagerAdapter: FragmentStatePagerAdapter
    private val mSeekListener: SeekBar.OnSeekBarChangeListener = object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
            isSeeking = true
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
            isSeeking = false
            seekBar?.let { PlayManager.instance.seekTo(seekBar.progress) }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        StatusBarCompat.setStatusBarColor(
            this,
            Color.parseColor(BaseApp.context.getString(R.color.accent_always_black as Int))
        )

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_detail)
        likeIV = find(R.id.iv_activity_play_detail_like)
        mSeekBar = find(R.id.sb_play_detail)
        playIV = find(R.id.iv_play)
        backIV = find(R.id.iv_back)
        forwardIV = find(R.id.iv_forward)
        vp = find(R.id.vp_play_detail)
        initVP()
        setListener()
        val song = PlayManager.instance.currentSong
        mSeekBar.setOnSeekBarChangeListener(mSeekListener)
        playIV.isSelected = PlayManager.instance.isPlaying
        checkLike()
        initToolbar()
        song?.let { showSong(it) }
    }

    override fun onResume() {
        super.onResume()
        PlayManager.instance.registerCallback(this)
        PlayManager.instance.registerProgressCallback(this)

    }

    override fun onPause() {
        super.onPause()
        PlayManager.instance.unregisterCallback(this)
        PlayManager.instance.unregisterProgressCallback(this)
    }

    private fun initToolbar() {
        toolbar_common.initBlack(
            title = "歌曲",
            listener = View.OnClickListener { finish() }
        )
    }

    private fun setListener() {
        likeIV.setOnClickListener {
            if (getBoolean()) {
                likeIV.setImageResource(R.drawable.activity_play_detail_like_off)
                PlayManager.instance.currentSong?.let { CollectSong.instance.deleteCollectSong(it) }
            } else {
                likeIV.setImageResource(R.drawable.activity_play_detail_like_on)
                PlayManager.instance.currentSong?.let { CollectSong.instance.addCollectSong(it) }
            }
        }
        playIV.setOnClickListener {
            PlayManager.instance.dispatch()

        }
        backIV.setOnClickListener {
            PlayManager.instance.previous()
        }
        forwardIV.setOnClickListener {
            PlayManager.instance.next()
        }
        iv_info.setOnClickListener {
            val bottomDialog = BottomSheetDialog(this)
            val view = layoutInflater.inflate(R.layout.dialog_bottom_song_list, null)
            bottomDialog.setContentView(view)
            val mBehavior = BottomSheetBehavior.from(view.parent as View)
            mBehavior.peekHeight = dp2px(450)
            setBottomClickListener(view)



            PlayManager.instance.setRule(Rulers.rule.rule)
            songList = view.find(R.id.rv_dialog_bottom)
            collectSongList = view.find(R.id.iv_collect)
            setCSLListener()
            bottomAdapter = DialogBottomAdapter()
            songList.layoutManager = LinearLayoutManager(this)
            songList.adapter = bottomAdapter
            bottomAdapter?.let {
                it.setBottomClickListener(object : OnBottomClickListener {
                    override fun onItemClick(position: Int) {
                        PlayManager.instance.dispatch(it.list[position])
                    }

                    override fun onDeleteClick(position: Int) {
                        PlayManager.instance.deleteSong(it.list[position])
                    }
                })
            }

            bottomDialog.show()

            setPositionRVBottom(view)
        }
        iv_loop.setOnClickListener {
            if (Rulers.rule.rule == LIST_LOOP) {
                PlayManager.instance.setRule(SINGLE_LOOP)
                toast("单曲循环")
            } else if (Rulers.rule.rule == SINGLE_LOOP) {
                PlayManager.instance.setRule(RANDOM)
                toast("随机播放")

            } else if (Rulers.rule.rule == RANDOM) {
                PlayManager.instance.setRule(LIST_LOOP)
                toast("列表循环")

            }
        }


    }
    //收藏歌单
    private fun setCSLListener() {
        collectSongList?.setOnClickListener {
            Rulers.mCSL?.let {
                Log.d("roger", it.toString())
                CollectSongList.instance.addCSL(it)
                toast("歌单已收藏")
            }
        }
    }


    private fun showSong(song: Song) {
        tv_toolbar_title.text = song.name
        val stringBuilder = StringBuilder()
        for (x in song.singer.withIndex()) {
            stringBuilder.append(x.value.name)
            if (x.index != (song.singer.size - 1)) {
                stringBuilder.append("/")
            }
        }
        tv_toolbar_singer.text = stringBuilder
    }

    private fun setBottomClickListener(view: View) {
        bottom_loop = view.find(R.id.iv_bottom_loop) as ImageView
        bottom_loop?.setOnClickListener {
            if (Rulers.rule.rule == LIST_LOOP) {
                PlayManager.instance.setRule(SINGLE_LOOP)
            } else if (Rulers.rule.rule == SINGLE_LOOP) {
                PlayManager.instance.setRule(RANDOM)
            } else if (Rulers.rule.rule == RANDOM) {
                PlayManager.instance.setRule(LIST_LOOP)
            }
        }
    }

    private fun initVP() {
//        pagerAdapter = object : FragmentPagerAdapter(
//            supportFragmentManager,
//            BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
//        ) {
//            override fun getItemId(position: Int): Long {
//                return PlayManager.instance.currentSong?.url.hashCode().toLong()
//            }
//
//            override fun getItemPosition(`object`: Any): Int {
//                return Rulers.mCurrentList.indexOf(PlayManager.instance.currentSong)
//            }
//
//            override fun getItem(position: Int): Fragment {
//                val fragment = PlayDetailImageFragment()
//                val bundle = Bundle()
//                bundle.putString("imgUrl", Rulers.mCurrentList[position].pic)
//                fragment.arguments = bundle
//                return fragment
//            }
//
//            override fun getCount(): Int {
//                return Rulers.mCurrentList.size
//            }
//        }
        val fList = mutableListOf<PlayDetailImageFragment>()
        for (x in Rulers.mCurrentList) {
            fList.add(PlayDetailImageFragment.newInstance(x.pic))
        }
        pagerAdapter = PlayDetailViewPagerAdapter(fList, supportFragmentManager)
        vp.adapter = pagerAdapter
        vp.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                PlayManager.instance.startAsong(Rulers.mCurrentList[position])
            }

        })
        vp.setCurrentItem(Rulers.getCurrentPos(), false)
    }


    //让bottomsheet滚动到红色item的位置，并尝试置顶
    private fun setPositionRVBottom(view: View) {
        val recyclerView: RecyclerView = view.find(R.id.rv_dialog_bottom)
        (recyclerView.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(Rulers.getCurrentPos(), 0)
        Log.d("roger", "currentpos === ${Rulers.getCurrentPos()}")
    }

}