package com.rg.musiound.interfaces.view

import com.rg.musiound.base.IBaseView
import com.rg.musiound.bean.Song

/**
 * Create by roger
 * on 2019/8/16
 */
interface IActivityPlayDetailView : IBaseView {
    fun setSong(song: Song)
    fun showError()
}