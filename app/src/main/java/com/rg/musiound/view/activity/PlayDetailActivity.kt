package com.rg.musiound.view.activity

import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.rg.musiound.R
import com.rg.musiound.service.PlayManager
import com.rg.musiound.service.Song
import com.rg.musiound.service.PlayService
import org.jetbrains.anko.find




/**
 * Create by roger
 * on 2019/8/16
 */
class PlayDetailActivity : AppCompatActivity(), PlayManager.Callback, PlayManager.ProgressCallback {
    override fun onPlayListPrepared(songs: List<Song>?) {

    }

    override fun onPlayStateChanged(state: Int, song: Song?) {
        when (state) {
            PlayService.STATE_INITIALIZED -> {
//                showSong(song)
            }
//            PlayService.STATE_STARTED -> mPlayPauseIv.setSelected(PlayManager.getInstance(this).isPlaying())
//            PlayService.STATE_PAUSED -> mPlayPauseIv.setSelected(PlayManager.getInstance(this).isPlaying())
//            PlayService.STATE_COMPLETED -> mPlayPauseIv.setSelected(PlayManager.getInstance(this).isPlaying())
//            PlayService.STATE_STOPPED -> mPlayPauseIv.setSelected(PlayManager.getInstance(this).isPlaying())
            PlayService.STATE_RELEASED -> {
//                mPlayPauseIv.setSelected(PlayManager.getInstance(this).isPlaying())
                mSeekBar.progress = 0
            }
            PlayService.STATE_ERROR -> {
//                mPlayPauseIv.setSelected(PlayManager.getInstance(this).isPlaying())
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
        mSeekBar  = find(R.id.sb_play_detail)
        mSeekBar.setOnSeekBarChangeListener(mSeekListener)
        PlayManager.getInstance(this)
        val song = Song("abs", "http://music.163.com/song/media/outer/url?id=441552.mp3", "sss")
        PlayManager.getInstance(this).add(listOf<Song>(song))
        PlayManager.getInstance(this).dispatch()
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


}