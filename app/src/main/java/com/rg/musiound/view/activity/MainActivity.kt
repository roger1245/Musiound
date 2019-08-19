package com.rg.musiound.view.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.rg.musiound.R
import com.rg.musiound.view.BaseActivity
import com.rg.musiound.view.fragment.DiscoveryFragment
import com.rg.musiound.view.fragment.MusicRankFragment
import com.rg.musiound.view.fragment.MyFragment
import kotlinx.android.synthetic.main.activity_main.*

import org.jetbrains.anko.find

class MainActivity : BaseActivity() {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager
    private val fragments = listOf(
        MyFragment(),
        DiscoveryFragment(),
        MusicRankFragment()
    )
    private val titles = listOf(
        "我的",
        "发现",
        "MV"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tabLayout = find(R.id.tl_activity_main)
        viewPager = find(R.id.vp_activity_main)
        initVP()
    }
    private fun initVP() {
        val adapter = object : FragmentStatePagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return fragments[position]
            }

            override fun getCount(): Int {
                return fragments.size
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return titles[position]
            }
        }
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
    }
}
