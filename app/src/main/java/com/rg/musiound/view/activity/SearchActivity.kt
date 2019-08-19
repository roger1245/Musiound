package com.rg.musiound.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.SearchEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import com.rg.musiound.R
import com.rg.musiound.view.BaseActivity
import com.rg.musiound.view.fragment.*
import com.rg.musiound.view.widget.MessageEvent
import kotlinx.android.synthetic.main.activity_search.*
import org.greenrobot.eventbus.EventBus

class SearchActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        initVP()
        initListener()
    }
    private fun initListener() {
        et_search.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if(actionId == EditorInfo.IME_ACTION_SEARCH) {
                    val key = et_search.text.toString().trim()
//                    if (key.isEmpty())
                    EventBus.getDefault().post(MessageEvent(et_search.text.toString()))
                    return true
                }
                return false
            }

        })
    }

    private val fragments = listOf<Fragment>(
        SearchSongFragment(),
        SearchSongListFragment()
    )
    private val titles = listOf(
        "单曲",
        "歌单"
    )

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
        vp_search.adapter = adapter
        tl_search.setupWithViewPager(vp_search)
    }
}
