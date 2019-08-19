package com.rg.musiound.interfaces.generic

import com.rg.musiound.base.IBaseView

/**
 * Create by roger
 * on 2019/8/18
 */
interface IActivityGenericView <T>: IBaseView {
    fun setData(songs: T)
    fun showError()
}