package com.rg.musiound.interfaces.model

import com.rg.musiound.base.BaseModel
import com.rg.musiound.base.IBaseModel
import com.rg.musiound.util.GenericPageCallback
import com.rg.musiound.util.SongListCallback

/**
 * Create by roger
 * on 2019/8/19
 */
interface IFragmentSongListModel<T> : IBaseModel {
    fun getData(callback: GenericPageCallback<T>, page: Int, category: String)
}