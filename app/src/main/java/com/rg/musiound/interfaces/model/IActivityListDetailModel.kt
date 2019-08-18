package com.rg.musiound.interfaces.model

import com.rg.musiound.base.IBaseModel
import com.rg.musiound.bean.SongList
import com.rg.musiound.util.SongsCallback

/**
 * Create by roger
 * on 2019/8/17
 */
interface IActivityListDetailModel: IBaseModel {
    fun getData(callback: SongsCallback, songList: Long)
}