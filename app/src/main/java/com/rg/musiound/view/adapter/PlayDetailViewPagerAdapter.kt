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
class PlayDetailViewPagerAdapter(val list: List<PlayDetailImageFragment>, fm: FragmentManager) : FragmentStatePagerAdapter(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return list[position]
    }

    override fun getCount(): Int {
        return list.size
    }
}