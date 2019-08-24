package com.rg.musiound.view.activity

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
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
import android.view.Window
import android.widget.ImageView
import androidx.annotation.RequiresApi
import com.githang.statusbar.StatusBarCompat
import com.rg.musiound.BaseApp
import kotlinx.android.synthetic.main.activity_main_tool_bar.*
import android.content.res.Configuration
import android.content.res.Configuration.*
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.NotificationManagerCompat
import com.rg.musiound.BaseApp.Companion.context
import com.rg.musiound.util.extensions.editor
import com.rg.musiound.util.extensions.sharedPreferences


class MainActivity : BaseActivity() {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager
    private lateinit var nightSwitch: ImageView
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
        checkIsDay()
        setContentView(R.layout.activity_main)
        tabLayout = find(R.id.tl_activity_main)
        viewPager = find(R.id.vp_activity_main)
        nightSwitch = find(R.id.iv_night_switch)
        initVP()
        iv_search.setOnClickListener {
            val intent = Intent(this@MainActivity, SearchActivity::class.java)
            startActivity(intent)
        }
        nightSwitch.setOnClickListener {
            setNightMode()
        }

    }

    private fun initVP() {
        val adapter = object : FragmentStatePagerAdapter(
            supportFragmentManager,
            FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        ) {
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

    //动态设置夜间模式
    private fun setNightMode() {
        val mode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        if (mode == UI_MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            context.sharedPreferences("IsDay").editor {
                putBoolean("IsDay", true)
            }
        } else if (mode == UI_MODE_NIGHT_NO) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            context.sharedPreferences("IsDay").editor {
                putBoolean("IsDay", false)
            }
        }
        recreate()
    }

    fun checkIsDay()  {
        val mode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        if (mode == Configuration.UI_MODE_NIGHT_YES) {
            StatusBarCompat.setStatusBarColor(this, Color.parseColor(BaseApp.context.getString(R.color.accent_always_black as Int)))
        } else if (mode == Configuration.UI_MODE_NIGHT_NO) {
            StatusBarCompat.setStatusBarColor(this, Color.parseColor(BaseApp.context.getString(R.color.accent_always_white as Int)))
        }
    }
}
