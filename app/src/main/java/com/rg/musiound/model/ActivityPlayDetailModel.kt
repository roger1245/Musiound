package com.rg.musiound.model

import com.rg.musiound.base.BaseModel
import com.rg.musiound.bean.Song
import com.rg.musiound.interfaces.model.IActivityPlayDetailModel
import com.rg.musiound.util.SongCallback

/**
 * Create by roger
 * on 2019/8/16
 */
class ActivityPlayDetailModel : BaseModel(), IActivityPlayDetailModel {
    override fun getData(callback: SongCallback) {
//        val song: Song = Song("abs", "https://m7.music.126.net/20190816172500/3b6ce34c5fe3f0365c7c310eb1c21563/ymusic/8972/6e6e/7b86/bddf788bf92e62d7c5c9aa457dd27bf5.mp3", "dd")
//        callback.onSuccess(song)
    }
}