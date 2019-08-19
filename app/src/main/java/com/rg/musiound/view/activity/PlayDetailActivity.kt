package com.rg.musiound.view.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import com.rg.musiound.R
import com.rg.musiound.bean.Song
import com.rg.musiound.service.PlayManager
import com.rg.musiound.service.PlayService
import com.rg.musiound.view.BaseActivity
import kotlinx.android.synthetic.main.activity_play_detail.*
import org.jetbrains.anko.find


/**
 * Create by roger
 * on 2019/8/16
 */
class PlayDetailActivity : BaseActivity(), PlayManager.Callback, PlayManager.ProgressCallback {
    override fun onPlayListPrepared(songs: List<Song>?) {

    }

    override fun onPlayStateChanged(state: Int, song: Song?) {
        when (state) {
            PlayService.STATE_INITIALIZED -> {
                song?.let { showSong(song) }
            }
            PlayService.STATE_STARTED -> {

                playIV.setSelected(PlayManager.getInstance(this).isPlaying)
            }
            PlayService.STATE_PAUSED -> {

                playIV.setSelected(PlayManager.getInstance(this).isPlaying)
            }

            PlayService.STATE_COMPLETED -> playIV.setSelected(PlayManager.getInstance(this).isPlaying)
            PlayService.STATE_STOPPED -> playIV.setSelected(PlayManager.getInstance(this).isPlaying)
            PlayService.STATE_RELEASED -> {
                playIV.setSelected(PlayManager.getInstance(this).isPlaying)
                mSeekBar.progress = 0
            }
            PlayService.STATE_ERROR -> {
                playIV.setSelected(PlayManager.getInstance(this).isPlaying)
                mSeekBar.progress = 0
            }
        }
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
    private val mSeekListener: SeekBar.OnSeekBarChangeListener = object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
            isSeeking = true
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
            isSeeking = false
            seekBar?.let { PlayManager.getInstance(seekBar.getContext()).seekTo(seekBar.progress) }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_detail)
        mSeekBar = find(R.id.sb_play_detail)
        playIV = find(R.id.iv_play)
        backIV = find(R.id.iv_back)
        forwardIV = find(R.id.iv_forward)
        setListener()
        val song = PlayManager.getInstance(this).currentSong

        mSeekBar.setOnSeekBarChangeListener(mSeekListener)
        playIV.isSelected = PlayManager.getInstance(this).isPlaying
//        initToolbar()
        song?.let { showSong(it) }
    }

    override fun onResume() {
        super.onResume()
        PlayManager.getInstance(this).registerCallback(this)
        PlayManager.getInstance(this).registerProgressCallback(this)

    }

    override fun onPause() {
        super.onPause()
        PlayManager.getInstance(this).unregisterCallback(this)
        PlayManager.getInstance(this).unregisterProgressCallback(this)
    }

    private fun initToolbar() {
        common_toolbar.init(
            title = "歌曲",
            listener = View.OnClickListener { finish() }
        )
    }

    private fun setListener() {
        playIV.setOnClickListener {
            PlayManager.getInstance(this).dispatch()

        }
        backIV.setOnClickListener {
            PlayManager.getInstance(this).previous()
        }
        forwardIV.setOnClickListener {
            PlayManager.getInstance(this).next()
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

}