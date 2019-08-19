package com.rg.musiound.interfaces.generic

import com.rg.musiound.base.IBaseModel
import com.rg.musiound.util.GenericCallback

/**
 * Create by roger
 * on 2019/8/18
 */
interface IActivityGenericModel<T, P>: IBaseModel {
    fun getData(uri: P, page: Int, callback: GenericCallback<T>)
}