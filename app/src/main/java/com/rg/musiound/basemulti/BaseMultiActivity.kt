package com.rg.musiound.basemulti

import android.os.Bundle
import com.rg.musiound.base.IBaseModel
import com.rg.musiound.base.IBasePresenter
import com.rg.musiound.base.IBaseView
import com.rg.musiound.view.BaseActivity

/**
 * Create by roger
 * on 2019/8/18
 */
abstract class BaseMultiActivity<V: IBaseView, P: IBasePresenter<V, M>, M: IBaseModel>: BaseActivity() {
    protected var list: List<P>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        list = createPresenter()
        list?.let {
            for (x in it) {
                x.attachView(getViewToAttach())
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        list?.let {
            for (x in it) {
                x.detachView()
            }
        }

    }

    /**
     * 返回需要绑定的IBaseView对象
     */
    abstract fun getViewToAttach(): V

    /**
     * 创建IBasePresenter的对象
     */
    abstract fun createPresenter(): List<P>
}