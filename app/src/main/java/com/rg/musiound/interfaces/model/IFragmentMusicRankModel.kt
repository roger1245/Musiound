package com.rg.musiound.interfaces.model

import com.rg.musiound.base.IBaseModel
import com.rg.musiound.util.MVListCallback

/**
 * Create by roger
 * on 2019/8/18
 */
interface IFragmentMusicRankModel : IBaseModel {
    fun getData(callback: MVListCallback)

}