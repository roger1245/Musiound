package com.rg.musiound.interfaces.model

import com.rg.musiound.base.IBaseModel
import com.rg.musiound.util.SongCallback

/**
 * Create by roger
 * on 2019/8/16
 */
interface IActivityPlayDetailModel : IBaseModel {
    fun getData(callback: SongCallback)
}