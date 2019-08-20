package com.rg.musiound.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import androidx.annotation.IntDef
import java.lang.annotation.RetentionPolicy
import com.rg.musiound.service.PlayService.PlayStateChangeListener
import android.telecom.VideoProfile.isPaused
import android.R.attr.start
import android.media.AudioManager
import android.util.Log
import java.io.IOException


/**
 * Create by roger
 * on 2019/8/15
 */
class PlayService : Service(), MediaPlayer.OnInfoListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener,
        MediaPlayer.OnErrorListener, MediaPlayer.OnSeekCompleteListener{

    companion object {
        const val STATE_IDLE = 0
        const val STATE_INITIALIZED = 1
        const val STATE_PREPARING = 2
        const val STATE_PREPARED = 3
        const val STATE_STARTED = 4
        const val STATE_PAUSED = 5
        const val STATE_STOPPED = 6
        const val STATE_COMPLETED = 7
        const val STATE_RELEASED = 8
        const val STATE_ERROR = -1

        @IntDef(
            STATE_IDLE,
            STATE_INITIALIZED,
            STATE_PREPARING,
            STATE_PREPARED,
            STATE_STARTED,
            STATE_PAUSED,
            STATE_STOPPED,
            STATE_COMPLETED,
            STATE_RELEASED,
            STATE_ERROR
        )
        @Retention(AnnotationRetention.SOURCE)
        annotation class State
    }

    private @State var mState = STATE_IDLE
    private var mPlayer: MediaPlayer? = null
    private var mBinder: PlayBinder? = null
    private var mStateListener: PlayStateChangeListener? = null

    private fun setPlayerState(@State state: Int) {
        if (mState == state) {
            return
        }
        mState = state
        mStateListener?.onStateChanged(mState)
    }

    override fun onBind(intent: Intent?): IBinder? {
        if (null == mBinder) {
            mBinder = PlayBinder()
        }
        return mBinder
    }

    override fun onInfo(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        return false
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onDestroy() {
        mStateListener?.onShutdown()
        super.onDestroy()
        stopForeground(true)
    }

    override fun onPrepared(mp: MediaPlayer?) {
        setPlayerState(STATE_PREPARED)
        doStartPlayer()
        Log.d("roger", "start" + mPlayer?.duration)
    }

    override fun onCompletion(mp: MediaPlayer?) {
        setPlayerState(STATE_COMPLETED)
    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        Log.d("roger", "onError")
        setPlayerState(STATE_ERROR)
        return false
    }

    override fun onSeekComplete(mp: MediaPlayer?) {
    }

    fun ensurePlayer() {
        if (null == mPlayer) {
            mPlayer = MediaPlayer()
        }
        setPlayerState(STATE_IDLE)
        mPlayer?.setOnInfoListener(this)
        mPlayer?.setOnPreparedListener(this)
        mPlayer?.setOnCompletionListener(this)
        mPlayer?.setOnErrorListener(this)
        mPlayer?.setOnSeekCompleteListener(this)
    }
    fun startPlayer(path: String) {
        Log.d("roger", "start  player")
//        mPlayer?.reset()
//        releasePlayer();
        ensurePlayer()
        try {
            mPlayer?.setDataSource(path)
            setPlayerState(STATE_INITIALIZED)
            mPlayer?.prepareAsync()
            Log.d("roger", "startPrepareAsync")
            setPlayerState(STATE_PREPARING)
        } catch (e: IOException) {
            e.printStackTrace()
            releasePlayer()
        }

    }

    private fun doStartPlayer() {
        mPlayer?.start()
        Log.d("roger", "dostartPlayer")
        val time = mPlayer?.duration
        time?.let { PlayManager.instance.setDuration(time) }
        setPlayerState(STATE_STARTED)
    }

    fun resumePlayer() {
        if (isPaused()) {
            doStartPlayer()
        }
    }

    fun pausePlayer() {
        if (isStarted()) {
            mPlayer?.pause()
            setPlayerState(STATE_PAUSED)
        }
    }
    fun stopPlayer() {
        if (isStarted() || isPaused()) {
            mPlayer?.stop()
            setPlayerState(STATE_STOPPED)
        }
    }

    fun releasePlayer() {
        if (mPlayer != null) {
            mPlayer?.release()
            mPlayer = null
            setPlayerState(STATE_RELEASED)
        }
    }

    fun isStarted(): Boolean {
        return STATE_STARTED == mState
    }

    fun isPaused(): Boolean {
        return mState == STATE_PAUSED
    }

    fun isReleased(): Boolean {
        return mState == STATE_RELEASED
    }

    @State
    fun getState(): Int {
        return mState
    }

    fun getPosition(): Int {
        if (mPlayer == null) {
            return 0
        }

        try {
            return mPlayer?.getCurrentPosition()!!
        } catch (e: Exception) {
            e.printStackTrace()
            return 0
        }

    }

    fun seekTo(position: Int) {
        if (isStarted() || isPaused()) {
            mPlayer?.seekTo(position)
        }
    }

    fun setPlayStateChangeListener(listener: PlayStateChangeListener?) {
        mStateListener = listener
    }

    interface PlayStateChangeListener {
        fun onStateChanged(@State state: Int)
        fun onShutdown()
    }

    inner class PlayBinder : Binder() {
        val service: PlayService
            get() = this@PlayService
    }


}