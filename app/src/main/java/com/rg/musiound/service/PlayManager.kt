package com.rg.musiound.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.media.AudioManager
import android.os.Handler
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import com.rg.musiound.BaseApp
import com.rg.musiound.bean.Song
import com.rg.musiound.db.PlayingSong
import com.rg.musiound.service.ruler.Rulers
import com.rg.musiound.service.ruler.Rulers.mCurrentList


/**
 * Create by roger
 * on 2019/8/15
 */
class PlayManager private constructor(private val mContext: Context) : PlayService.PlayStateChangeListener {

    private val mConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            mService = (service as PlayService.PlayBinder).service
            mService!!.setPlayStateChangeListener(this@PlayManager)
//            startRemoteControl()
            if (!isPlaying) {
                dispatch(currentSong)
            }
        }

        override fun onServiceDisconnected(name: ComponentName) {
            mService!!.setPlayStateChangeListener(null)
            mService = null

            startPlayService()
            bindPlayService()
        }
    }

//    private val mNoisyReceiver = object : SimpleBroadcastReceiver() {
//
//        fun onReceive(context: Context, intent: Intent) {
//            if (AudioManager.ACTION_AUDIO_BECOMING_NOISY == intent.action) {
//                // Pause the playback
//                pause(false)
//            }
//        }
//
//    }

    private val mAfListener = AudioManager.OnAudioFocusChangeListener { focusChange ->
        if ((focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS)) {
            if (isPlaying) {
                pause(false)
            }
        } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {

        } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
            if (isPaused && !isPausedByUser) {
                resume()
            }
        }
    }

    private val mPeriod = 1000
    private var isProgressUpdating = false
    private val mProgressRunnable = object : Runnable {
        override fun run() {
            if (mCallbacks != null && !mCallbacks!!.isEmpty()
                && mService != null && currentSong != null && mService!!.isStarted()
            ) {
                for (callback in mProgressCallbacks) {
                    duration?.let { callback.onProgress(mService!!.getPosition(), it) }
                }
                mHandler!!.postDelayed(this, mPeriod.toLong())
                isProgressUpdating = true
            } else {
                isProgressUpdating = false
            }
        }
    }

    private var mHandler: Handler? = null

    private val mCallbacks: MutableList<Callback>?
    private val mProgressCallbacks: MutableList<ProgressCallback>
    private var totalList: MutableList<Song> = mutableListOf()

    /**
     *
     * @return a song current playing or paused, may be null
     */
    var currentSong: Song? = null
        private set
    private var mState = PlayService.STATE_IDLE
    private var mService: PlayService? = null
    private var duration: Int? = null

    var isPausedByUser = false
        private set

//
//    var notificationAgent: NotificationAgent? = null
//
//    var mediaSessionCompat: MediaSessionCompat? = null
//        private set

    /**
     *
     * @return
     */
    val isPlaying: Boolean
        get() = mService != null && mService!!.isStarted()

    val isPaused: Boolean
        get() = mService != null && mService!!.isPaused()

//    private val mSessionCallback = object : MediaSessionCompat.Callback() {
//        fun onCommand(command: String, extras: Bundle, cb: ResultReceiver) {
//            super.onCommand(command, extras, cb)
//            Log.v(TAG, "mSessionCallback onCommand command=$command")
//        }
//
//        fun onMediaButtonEvent(mediaButtonEvent: Intent): Boolean {
//            Log.v(TAG, "mSessionCallback onMediaButtonEvent mediaButtonEvent=" + mediaButtonEvent.action!!)
//            return super.onMediaButtonEvent(mediaButtonEvent)
//        }
//
//        fun onPrepare() {
//            super.onPrepare()
//            Log.v(TAG, "mSessionCallback onPrepare")
//        }
//
//        fun onPrepareFromMediaId(mediaId: String, extras: Bundle) {
//            super.onPrepareFromMediaId(mediaId, extras)
//            Log.v(TAG, "mSessionCallback onPrepareFromMediaId mediaId=$mediaId")
//        }
//
//        fun onPrepareFromSearch(query: String, extras: Bundle) {
//            super.onPrepareFromSearch(query, extras)
//            Log.v(TAG, "mSessionCallback onPrepareFromSearch query=$query")
//        }
//
//        fun onPrepareFromUri(uri: Uri, extras: Bundle) {
//            super.onPrepareFromUri(uri, extras)
//            Log.v(TAG, "mSessionCallback onPrepareFromUri uri=" + uri.toString())
//        }
//
//        fun onPlay() {
//            super.onPlay()
//            dispatch()
//            Log.v(TAG, "mSessionCallback onPlay")
//        }
//
//        fun onPlayFromMediaId(mediaId: String, extras: Bundle) {
//            super.onPlayFromMediaId(mediaId, extras)
//            Log.v(TAG, "mSessionCallback onPlayFromMediaId mediaId=$mediaId")
//        }
//
//        fun onPlayFromSearch(query: String, extras: Bundle) {
//            super.onPlayFromSearch(query, extras)
//            Log.v(TAG, "mSessionCallback onPlayFromSearch query=$query")
//        }
//
//        fun onPlayFromUri(uri: Uri, extras: Bundle) {
//            super.onPlayFromUri(uri, extras)
//            Log.v(TAG, "mSessionCallback onPlayFromUri uri=" + uri.toString())
//        }
//
//        fun onSkipToQueueItem(id: Long) {
//            super.onSkipToQueueItem(id)
//            Log.v(TAG, "mSessionCallback onSkipToQueueItem id=$id")
//        }
//
//        fun onPause() {
//            pause(true)
//            Log.v(TAG, "mSessionCallback onPause")
//        }
//
//        fun onSkipToNext() {
//            next()
//            Log.v(TAG, "mSessionCallback onSkipToNext")
//        }
//
//        fun onSkipToPrevious() {
//            previous()
//            Log.v(TAG, "mSessionCallback onSkipToPrevious")
//        }
//
//        fun onFastForward() {
//            super.onFastForward()
//            Log.v(TAG, "mSessionCallback onFastForward")
//        }
//
//        fun onRewind() {
//            super.onRewind()
//            Log.v(TAG, "mSessionCallback onRewind")
//        }
//
//        fun onStop() {
//            super.onStop()
//            Log.v(TAG, "mSessionCallback onStop")
//        }
//
//        fun onSeekTo(pos: Long) {
//            super.onSeekTo(pos)
//            Log.v(TAG, "mSessionCallback onSeekTo pos=$pos")
//        }
//
//        fun onSetRating(rating: RatingCompat) {
//            super.onSetRating(rating)
//            Log.v(TAG, "mSessionCallback onSetRating rating=" + rating.toString())
//        }
//
//        fun onCustomAction(action: String, extras: Bundle) {
//            super.onCustomAction(action, extras)
//            Log.v(TAG, "mSessionCallback onCustomAction action=$action")
//        }
//    }

    init {
        mCallbacks = ArrayList()
        mProgressCallbacks = ArrayList()
        mHandler = Handler()

    }


    private fun bindPlayService() {
        mContext.bindService(Intent(mContext, PlayService::class.java), mConnection, Context.BIND_AUTO_CREATE)
    }

    private fun unbindPlayService() {
        if (mService != null) {
            mContext.unbindService(mConnection)
        }
    }

    private fun startPlayService() {
        mContext.startService(Intent(mContext, PlayService::class.java))
    }

    private fun stopPlayService() {
        mContext.stopService(Intent(mContext, PlayService::class.java))
    }


    fun dispatch(song: Song? = currentSong) {
        if (mCurrentList.isEmpty() || song == null) {
//            song?.let { mCurrentList.add(song) }
//            song?.let{
//                currentSong = song
//                mService?.startPlayer(it.url)
//            }
            return
        }
        if (mService != null) {
            if (song == currentSong) {
                Log.d("roger", "3")

                if (mService!!.isStarted()) {
                    //Do really this action by user
                    pause()
                } else if (mService!!.isPaused()) {
                    resume()
                } else {
                    mService?.releasePlayer()
                    if (AudioManager.AUDIOFOCUS_REQUEST_GRANTED == requestAudioFocus()) {
                        currentSong = song
                        mService?.startPlayer(song.url)
                    }
                }
            } else {

                mService?.releasePlayer()
                if (AudioManager.AUDIOFOCUS_REQUEST_GRANTED == requestAudioFocus()) {
                    currentSong = song
                    song.let {
                        mService?.startPlayer(it.url)
                    }
                }
            }

        } else {
            currentSong = song
            bindPlayService()
            startPlayService()
        }

    }

    /**
     * next song by user action
     */
    operator fun next() {
        next(true)
    }

    /**
     * next song triggered by [.onStateChanged] and [PlayService.STATE_COMPLETED]
     * @param isUserAction
     */
    private fun next(isUserAction: Boolean) {
        var song: Song? = null
        currentSong?.let { song = Rulers.rule.next(it, mCurrentList, isUserAction) }
        song?.let { dispatch(it) }
    }

    /**
     * previous song by user action
     */
    fun previous() {
        previous(true)
    }

    private fun previous(isUserAction: Boolean) {
        var song: Song? = null
        currentSong?.let { song = Rulers.rule.previous(it, mCurrentList, isUserAction) }
        song?.let { dispatch(it) }
    }

    /**
     * resume play
     */
    fun resume() {
        if (AudioManager.AUDIOFOCUS_REQUEST_GRANTED == requestAudioFocus()) {
            mService!!.resumePlayer()
        }
    }

    /**
     * pause a playing song by user action
     */
    fun pause() {
        pause(true)
    }

    /**
     * pause a playing song
     * @param isPausedByUser false if triggered by [AudioManager.AUDIOFOCUS_LOSS] or
     * [AudioManager.AUDIOFOCUS_LOSS_TRANSIENT]
     */
    private fun pause(isPausedByUser: Boolean) {
        mService!!.pausePlayer()
        this.isPausedByUser = isPausedByUser
    }

    fun stop() {
        mService!!.stopPlayer()
    }

    /**
     * release a playing song
     */
    fun release() {
        mService!!.releasePlayer()
        unbindPlayService()
        stopPlayService()

        mService!!.setPlayStateChangeListener(null)
        mService = null
    }
//
//    private fun startRemoteControl() {
//        val mediaButtonReceiver = ComponentName(mContext, RemoteControlReceiver::class.java!!)
//        mediaSessionCompat = MediaSessionCompat(mContext, TAG, mediaButtonReceiver, null)
//        mediaSessionCompat!!.setFlags(
//            MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS or MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS
//        )
//        mediaSessionCompat!!.setCallback(mSessionCallback)
//        setSessionActive(true)
//        changeMediaSessionState(PlayService.STATE_IDLE)
//    }
//
//    private fun setSessionActive(active: Boolean) {
//        mediaSessionCompat!!.setActive(active)
//    }
//
//    private fun stopRemoteControl() {
//        if (mediaSessionCompat != null) {
//            mediaSessionCompat!!.release()
//        }
//    }
//
//    private fun changeMediaSessionMetadata(@PlayService.State state: Int) {
//        if (mediaSessionCompat == null || !mediaSessionCompat!!.isActive()) {
//            return
//        }
//        val hasSong = currentSong != null
//        val title = if (hasSong) currentSong!!.getTitle() else ""
//        val album = if (hasSong) currentSong!!.getAlbum() else ""
//        val artist = if (hasSong) currentSong!!.getArtist() else ""
//        var bmp: Bitmap? = null
//
//        if (hasSong) {
//            val albumObj = currentSong!!.getAlbumObj()
//            if (albumObj != null) {
//                val cover = albumObj!!.getAlbumArt()
//                if (!TextUtils.isEmpty(cover) && File(cover).exists()) {
//                    bmp = BitmapFactory.decodeFile(currentSong!!.getAlbumObj().getAlbumArt())
//                }
//            }
//
//        }
//        mediaSessionCompat!!.setMetadata(
//            MediaMetadataCompat.Builder()
//                .putString(MediaMetadataCompat.METADATA_KEY_TITLE, title)
//                .putString(MediaMetadataCompat.METADATA_KEY_ALBUM, album)
//                .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, artist)
//                .putBitmap(MediaMetadataCompat.METADATA_KEY_ALBUM_ART, bmp)
//                .build()
//        )
//        changeMediaSessionState(state)
//    }
//
//    private fun changeMediaSessionState(@PlayService.State state: Int) {
//        if (mediaSessionCompat == null || !mediaSessionCompat!!.isActive()) {
//            return
//        }
//        val playState = if (isPlaying) PlaybackStateCompat.STATE_PLAYING else PlaybackStateCompat.STATE_PAUSED
//        mediaSessionCompat!!.setPlaybackState(
//            PlaybackStateCompat.Builder()
//                .setState(playState, mService!!.getPosition(), 0)
//                .setActions(
//                    PlaybackStateCompat.ACTION_PLAY_PAUSE or
//                            PlaybackStateCompat.ACTION_SKIP_TO_NEXT or
//                            PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS
//                )
//                .build()
//        )
//    }

    fun seekTo(position: Int) {
        if (mService != null) {
            mService!!.seekTo(position)
        }
    }

    private fun requestAudioFocus(): Int {
        val audioManager = mContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        Log.v(TAG, "requestAudioFocus by ")
        return audioManager.requestAudioFocus(
            mAfListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN
        )
    }

    private fun releaseAudioFocus(): Int {
        val audioManager = mContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        Log.v(TAG, "releaseAudioFocus by ")
        return audioManager.abandonAudioFocus(mAfListener)
    }

    @JvmOverloads
    fun registerCallback(callback: Callback, updateOnceNow: Boolean = false) {
        if (mCallbacks!!.contains(callback)) {
            return
        }
        mCallbacks!!.add(callback)
        if (updateOnceNow) {
            callback.onPlayListPrepared(totalList)
//            callback.onPlayRuleChanged(rule)
            callback.onPlayStateChanged(mState, currentSong)
        }
    }

    fun unregisterCallback(callback: Callback) {
        if (mCallbacks!!.contains(callback)) {
            mCallbacks!!.remove(callback)
        }
    }

    private fun startUpdateProgressIfNeed() {
        if (!isProgressUpdating) {
            mHandler!!.post(mProgressRunnable)
        }
    }

    fun registerProgressCallback(callback: ProgressCallback) {
        if (mProgressCallbacks.contains(callback)) {
            return
        }
        mProgressCallbacks.add(callback)
        startUpdateProgressIfNeed()
    }

    fun unregisterProgressCallback(callback: ProgressCallback) {
        if (mProgressCallbacks.contains(callback)) {
            mProgressCallbacks.remove(callback)
        }
    }

    private fun registerNoisyReceiver() {
//        mNoisyReceiver.register(mContext, IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY))
    }

    private fun unregisterNoisyReceiver() {
//        mNoisyReceiver.unregister(mContext)
    }

    override fun onStateChanged(state: Int) {
        mState = state
        when (state) {
            PlayService.STATE_IDLE -> isPausedByUser = false
            PlayService.STATE_INITIALIZED -> {
                isPausedByUser = false
//                changeMediaSessionMetadata(state)
            }
            PlayService.STATE_PREPARING -> isPausedByUser = false
            PlayService.STATE_PREPARED -> isPausedByUser = false
            PlayService.STATE_STARTED -> {
                registerNoisyReceiver()
//                notification(state)
//                setSessionActive(true)
//                changeMediaSessionState(state)
                startUpdateProgressIfNeed()
                isPausedByUser = false
            }
            PlayService.STATE_PAUSED -> {
                unregisterNoisyReceiver()
//                notification(state)
//                changeMediaSessionState(state)
            }
            PlayService.STATE_ERROR -> {
                unregisterNoisyReceiver()
                releaseAudioFocus()
//                notification(state)
                isPausedByUser = false
            }
            PlayService.STATE_STOPPED -> {
                unregisterNoisyReceiver()
                releaseAudioFocus()
                //notification(state);
//                changeMediaSessionState(state)
//                setSessionActive(false)
                isPausedByUser = false
            }
            PlayService.STATE_COMPLETED -> {
                unregisterNoisyReceiver()
                releaseAudioFocus()
//                notification(state)
//                changeMediaSessionState(state)
                isPausedByUser = false
                next(false)
            }
            PlayService.STATE_RELEASED -> {
                Log.v(TAG, "onStateChanged STATE_RELEASED")
                unregisterNoisyReceiver()
                releaseAudioFocus()
                isPausedByUser = false
            }
        }
        for (callback in mCallbacks!!) {
            callback.onPlayStateChanged(state, currentSong)
        }
    }

    override fun onShutdown() {
        releaseAudioFocus()
//        stopRemoteControl()
        mService!!.stopForeground(true)
        val notifyManager = NotificationManagerCompat.from(mContext)
        notifyManager.cancelAll()
        for (callback in mCallbacks!!) {
            callback.onShutdown()
        }
    }

//    //private int mLastNotificationId;
//    private fun notification(@PlayService.State state: Int) {
//        if (notificationAgent == null) {
//            return
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            notificationLollipop(state)
//        } else {
//            notificationPreLollipop(state)
//        }
//    }
//
//    private fun notificationPreLollipop(@PlayService.State state: Int) {
//        val builder = notificationAgent!!.getBuilder(mContext, this, state, currentSong)
//        mService!!.startForeground(1, builder.build())
//    }
//
//    private fun notificationLollipop(@PlayService.State state: Int) {
//        val notifyManager = NotificationManagerCompat.from(mContext)
//        val builder = notificationAgent!!.getBuilder(mContext, this, state, currentSong)
//        notifyManager.notify(1, builder.build())
//    }

    interface Callback {
        fun onPlayListPrepared(songs: List<Song>?)
        fun onPlayStateChanged(state: Int, song: Song?)
        fun onShutdown()
        fun onPlayListChanged(list: List<Song>)
        fun onRuleChanged(rule: Int)
    }

    interface ProgressCallback {
        fun onProgress(progress: Int, duration: Int)
    }

    companion object {

        private val TAG = PlayManager::class.java!!.simpleName
//        private var sManager: PlayManager? = null
//
//        @Synchronized
//        fun getInstance(context: Context): PlayManager {
//            if (sManager == null) {
//                sManager = PlayManager(context.applicationContext)
//            }
//            return sManager as PlayManager
//        }
        val instance: PlayManager by lazy { PlayManager(BaseApp.context)}
    }

    fun add(song: List<Song>) {
        Rulers.add(song)
//        mCurrentList.addAll(song)
//        PlayingSong.instance.addAll(song)
    }
    fun add(song: Song) {
        Rulers.add(song)
//        mCurrentList.add(song)
//        PlayingSong.instance.addPlayingSong(song)
    }
    fun deleteSong(song: Song) {
        Rulers.delete(song)
//        mCurrentList.remove(song)
//        PlayingSong.instance.deletePlayingSong(song)
    }
    fun deleteAll() {
        Rulers.deleteAll()
//        mCurrentList.clear()
//        PlayingSong.instance.deleteAll()
    }
    fun getSongs(): List<Song> {
        return Rulers.mCurrentList
//        return PlayingSong.instance.getPlayingSong()
    }

    fun setDuration(time: Int) {
        duration = time
    }

    fun setRule(RULE: Int) {
        Rulers.setRule(RULE)
    }

    fun onPlayListChanged(list: List<Song>) {
        for (callback in mCallbacks!!) {
            callback.onPlayListChanged(list)
        }
    }
    fun onRuleChanged(rule: Int) {
        for (callback in mCallbacks!!) {
            callback.onRuleChanged(rule)
        }
    }


}

