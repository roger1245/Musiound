package com.rg.musiound.interfaces.view

import com.rg.musiound.base.IBaseView
import com.rg.musiound.bean.MV

/**
 * Create by roger
 * on 2019/8/18
 */
interface IFragmentMusicRankView : IBaseView {
    fun setVideoList(videos: List<MV>)
    fun showError()
}