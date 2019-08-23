package com.rg.musiound.service.ruler


import com.rg.musiound.bean.Song
import com.rg.musiound.db.CSList
import com.rg.musiound.db.PlayingSong
import com.rg.musiound.db.RecentPlayedSong
import com.rg.musiound.service.PlayManager
import java.util.Random
import java.util.Stack


const val SINGLE_LOOP = 0
const val LIST_LOOP = 1
const val RANDOM = 2
object Rulers {
    //由rules类与数据库进行单向回调
    var mCurrentList: MutableList<Song> = PlayingSong.instance.getPlayingSong()
            private set

    var mCSL: CSList? = null
    fun add(song: List<Song>) {
        mCurrentList.addAll(song)
        PlayingSong.instance.addAll(song)
        PlayManager.instance.onPlayListChanged(mCurrentList)
    }

    fun add(song: Song) {
        mCurrentList.add(song)
        PlayingSong.instance.addPlayingSong(song)
        PlayManager.instance.onPlayListChanged(mCurrentList)
    }

    fun delete(song: Song) {
        mCurrentList.remove(song)
        PlayingSong.instance.deletePlayingSong(song)
        PlayManager.instance.onPlayListChanged(mCurrentList)

    }

    fun delete(songs: List<Song>) {
        mCurrentList.removeAll(songs)
        PlayingSong.instance.deletePlayingSong(songs)
        PlayManager.instance.onPlayListChanged(mCurrentList)

    }

    fun deleteAll() {
        mCurrentList.clear()
        PlayingSong.instance.deleteAll()
        PlayManager.instance.onPlayListChanged(mCurrentList)

    }
    fun setRule(RULE: Int) {
        when (RULE) {
            SINGLE_LOOP -> rule = SingleLoopRuler()
            LIST_LOOP -> rule = ListLoopRuler()
            RANDOM -> rule = RandomRuler()
        }
        PlayManager.instance.onRuleChanged(RULE)
    }
    fun getSongs(): List<Song> {
        return PlayingSong.instance.getPlayingSong()
    }

    fun addRecentPlayedSong(song: Song) {
        RecentPlayedSong.instance.addRecentPlayedSong(song)
    }

    fun getCurrentPos(): Int {
        return  mCurrentList.indexOf(PlayManager.instance.currentSong)
    }

    var rule: Rule = ListLoopRuler()
        private set



    class SingleLoopRuler  constructor() : Rule {
        override val rule = SINGLE_LOOP

        override fun previous(song: Song, songList: List<Song>, isUserAction: Boolean): Song {
            return song
        }

        override fun next(song: Song, songList: List<Song>, isUserAction: Boolean): Song {
            return song
        }

        override fun clear() {

        }
    }

    class ListLoopRuler : Rule {
        override val rule = LIST_LOOP
        override fun previous(song: Song, songList: List<Song>, isUserAction: Boolean): Song {
            if (songList.isNotEmpty()) {
                var index = songList.indexOf(song)
                if (index < 0) {
                    return songList[0]
                } else if (index == 0) {
                    index = songList.size
                }
                return songList[index - 1]
            }
            return song
        }

        override fun next(song: Song, songList: List<Song>, isUserAction: Boolean): Song {
            if (!songList.isEmpty()) {
                val index = songList.indexOf(song)
                return if (index < 0) {
                    songList[0]
                } else songList[(index + 1) % songList.size]
            }
            return song
        }

        override fun clear() {

        }
    }

    class RandomRuler  : Rule {
        override val rule = RANDOM
        private val mRandom: Random
        private val mSongStack: Stack<Song>

        init {
            mRandom = Random()
            mSongStack = Stack()
        }

        override fun previous(song: Song, songList: List<Song>, isUserAction: Boolean): Song {
            if (songList.isEmpty()) {
                return song
            }
            if (!mSongStack.isEmpty()) {
                return mSongStack.pop()
            }
            val index = mRandom.nextInt(songList.size)

            return songList[index]
        }

        override fun next(song: Song, songList: List<Song>, isUserAction: Boolean): Song {
            if (songList.size > 1) {
                var forwardSong: Song
                if (!mSongStack.isEmpty()) {
                    val lastSong = mSongStack[mSongStack.size - 1]

                    do {
                        val index = mRandom.nextInt(songList.size)
                        forwardSong = songList[index]
                    } while (lastSong == forwardSong)
                } else {
                    val index = mRandom.nextInt(songList.size)
                    forwardSong = songList[index]
                }

                mSongStack.push(forwardSong)
                return forwardSong
            }
            return song
        }

        override fun clear() {
            mSongStack.clear()
        }
    }
}
