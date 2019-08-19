package com.rg.musiound.interfaces.generic

import com.rg.musiound.base.IBaseView

/**
 * Create by roger
 * on 2019/8/18
 */
interface IActivityGenericView <T, P>: IBaseView {
    fun setData(data: T, page: Int)
    fun showError()
}