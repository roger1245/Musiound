package com.rg.musiound.interfaces.model

import com.rg.musiound.base.IBaseModel
import com.rg.musiound.bean.MVDetailRaw
import com.rg.musiound.util.GenericCallback
import com.rg.musiound.util.GenericPageCallback

/**
 * Create by roger
 * on 2019/8/18
 */
interface IActivityMVPlayerModel<T1, T2>: IBaseModel {
    fun getMVDetail(id: Long, callback: GenericCallback<T1>)
    fun getMVComment(id: Long, callback: GenericPageCallback<T2>, page: Int = 0)
}