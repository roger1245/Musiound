package com.rg.musiound.service.ruler


import com.rg.musiound.service.Song
import java.util.Random
import java.util.Stack


object Rulers {

    val RULER_SINGLE_LOOP: Rule = SingleLoopRuler()
    val RULER_LIST_LOOP: Rule = ListLoopRuler()
//    val RULER_RANDOM: Rule = RandomRuler()

    class SingleLoopRuler  constructor() : Rule {

        override fun previous(song: Song, songList: List<Song>, isUserAction: Boolean): Song {
            return if (isUserAction) {
                RULER_LIST_LOOP.previous(song, songList, isUserAction)
            } else song
        }

        override fun next(song: Song, songList: List<Song>, isUserAction: Boolean): Song {
            return if (isUserAction) {
                RULER_LIST_LOOP.next(song, songList, isUserAction)
            } else song
        }

        override fun clear() {

        }
    }

    class ListLoopRuler  constructor() : Rule {

        override fun previous(song: Song, songList: List<Song>, isUserAction: Boolean): Song {
            if (!songList.isEmpty()) {
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
//
//    class RandomRuler private constructor() : Rule {
//
//        private val mRandom: Random
//        private val mSongStack: Stack<Song>
//
//        init {
//            mRandom = Random()
//            mSongStack = Stack()
//        }
//
//        fun previous(song: Song, songList: List<Song>?, isUserAction: Boolean): Song {
//            if (songList == null || songList.isEmpty()) {
//                return song
//            }
//            if (!mSongStack.isEmpty()) {
//                return mSongStack.pop()
//            }
//            val index = mRandom.nextInt(songList.size)
//
//            return songList[index]
//        }
//
//        fun next(song: Song, songList: List<Song>?, isUserAction: Boolean): Song? {
//            if (songList != null && songList.size > 1) {
//                var forwardSong: Song
//                if (!mSongStack.isEmpty()) {
//                    val lastSong = mSongStack[mSongStack.size - 1]
//
//                    do {
//                        val index = mRandom.nextInt(songList.size)
//                        forwardSong = songList[index]
//                    } while (lastSong == forwardSong)
//                } else {
//                    val index = mRandom.nextInt(songList.size)
//                    forwardSong = songList[index]
//                }
//
//                mSongStack.push(forwardSong)
//                return forwardSong
//            }
//            return song
//        }
//
//        override fun clear() {
//            mSongStack.clear()
//        }
//    }
}
