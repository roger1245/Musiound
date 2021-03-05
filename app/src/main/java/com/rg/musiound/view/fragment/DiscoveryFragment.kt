package com.rg.musiound.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.rg.musiound.R
import com.rg.musiound.view.songlist.SongListFragment
import kotlinx.android.synthetic.main.fragment_discovery.*

/**
 * Create by roger
 * on 2019/8/18
 */
class DiscoveryFragment : Fragment() {
    private val fragments = listOf(
        SongListFragment(),
        SongListFragment()
    )
    private val titles = listOf(
        "精品歌单",
        "热门歌单"
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_discovery, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initVP()
    }

    private fun initVP() {
        activity?.let {
            val adapter = object : FragmentPagerAdapter(it.supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
                override fun getItem(position: Int): Fragment {
                    val fragment = fragments[position]
                    val bundle = Bundle()
                    if (position == 0) {
                        bundle.putString("category", "highQuality")
                    } else if (position == 1) {
                        bundle.putString("category", "hot")
                    }
                    fragment.arguments = bundle
                    return fragment
                }

                override fun getCount(): Int {
                    return fragments.size
                }

                override fun getPageTitle(position: Int): CharSequence? {
                    return titles[position]
                }
            }
            vp_fragment_discovery.adapter = adapter
            tl_fragment_discovery.setupWithViewPager(vp_fragment_discovery)
        }
    }
}