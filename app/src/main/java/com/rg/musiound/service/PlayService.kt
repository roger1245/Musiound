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
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.media.AudioManager
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.rg.musiound.R
import com.rg.musiound.view.activity.PlayDetailActivity
import java.io.IOException
import java.lang.StringBuilder
import android.app.NotificationChannel
import androidx.annotation.RequiresApi






/**
 * Create by roger
 * on 2019/8/15
 */
const val ACTION_PAUSE = "com.rg.musiound.pause"
const val ACTION_PLAY_DETAIL = "com.rg.musiound.play_detail"
const val ACTION_NEXT = "com.rg.musiound.next"
const val ACTION_DELETE = "com.rg.musiound.delete"
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
    }

    override fun onCompletion(mp: MediaPlayer?) {
        setPlayerState(STATE_COMPLETED)
    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
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

    //notification
//    private lateinit var mNotificationManager: NotificationManager
    private var mNotification: Notification? = null
    private val CHANNEL_ID = "musiound"
    private val CHANNEL_NAME = "MUSIOUND"
    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            createNotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance)
        }

        startForeground(11, getNotification())
    }
//
//    fun updateNotification() {
//
//    }
//    fun cancelNotification() {
//        stopForeground(true)
//
//
//
//    }

    private val pause = 0
    private val play_detail = 2
    private val next = 3
    private val delete = 4
    private fun getNotification() : Notification {
        val song = PlayManager.instance.currentSong
        val remoteViews = RemoteViews(this.packageName, R.layout.notification)
        song?.let {
            val ar = it.singer.map { it.name}
            val stringBuilder = StringBuilder()
            for (x in ar.withIndex()) {
                stringBuilder.append(x.value)
                if (x.index != ar.size - 1) {
                    stringBuilder.append(",")
                }
            }
            remoteViews.setTextViewText(R.id.tv_no_name, it.name)
            remoteViews.setTextViewText(R.id.tv_no_artist, stringBuilder.toString())
        }
        //pause
        val pauseIntent = Intent(ACTION_PAUSE)
        val pausePendingIntent = PendingIntent.getBroadcast(this, pause, pauseIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        remoteViews.setOnClickPendingIntent(R.id.iv_no_play, pausePendingIntent)
        //next
        val nextIntent = Intent(ACTION_NEXT)
        val nextPendingIntent = PendingIntent.getBroadcast(this, next, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        remoteViews.setOnClickPendingIntent(R.id.iv_no_next, nextPendingIntent)

        //delete
        val deleteIntent = Intent(ACTION_DELETE)
        val deletePendingIntent = PendingIntent.getBroadcast(this, delete, deleteIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        remoteViews.setOnClickPendingIntent(R.id.iv_no_delete, deletePendingIntent)

        //PlayDetailActivity
        val contentIntent = Intent(this, PlayDetailActivity::class.java)
        val contentPendingIntent = PendingIntent.getActivity(this, play_detail, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT)



        if (mNotification == null) {
            val builder = NotificationCompat.Builder(this, CHANNEL_ID).setContent(remoteViews)
                .setSmallIcon(R.drawable.toolbar_common_ic_back)
                .setContentTitle("这是标题")
                .setContentText("这是内容")
                .setAutoCancel(false)
                .setContentIntent(contentPendingIntent)
                .setContent(remoteViews)
            mNotification = builder.build()
        } else {
        }
        return mNotification as Notification
    }


    //channel id
    //创建通知渠道
    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String, channelName: String, importance: Int) {
        val channel = NotificationChannel(channelId, channelName, importance)
        val notificationManager = getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }


}