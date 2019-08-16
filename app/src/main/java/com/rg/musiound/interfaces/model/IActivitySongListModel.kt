package com.rg.musiound.interfaces.model

import com.rg.musiound.base.BaseModel
import com.rg.musiound.base.IBaseModel
import com.rg.musiound.util.SongListCallback

/**
 * Create by roger
 * on 2019/8/16
 */
interface IActivitySongListModel : IBaseModel {
    fun getData(callback: SongListCallback)
}