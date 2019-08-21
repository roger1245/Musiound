package com.rg.musiound.view.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.rg.musiound.service.ruler.Rulers
import com.rg.musiound.view.fragment.PlayDetailImageFragment

/**
 * Create by roger
 * on 2019/8/21
 */
class PlayDetailViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {


    private var listFragment: List<Fragment> = mutableListOf()
    private var listId: List<Int> = mutableListOf()

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

    override fun getItem(position: Int): Fragment {
        val fragment = PlayDetailImageFragment()
        val bundle = Bundle()
        bundle.putString("imgUrl", Rulers.mCurrentList[position].pic)
        fragment.arguments = bundle
        return fragment
    }

    override fun getCount(): Int {
        return Rulers.mCurrentList.size
    }
}