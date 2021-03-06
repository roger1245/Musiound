package com.rg.musiound.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.rg.musiound.R
import com.rg.musiound.view.activity.*
import org.jetbrains.anko.find

/**
 * Create by roger
 * on 2019/8/18
 */
class MyFragment : Fragment() {
    private lateinit var llRecent: LinearLayout
    private lateinit var llCollct: LinearLayout
    private lateinit var llCollectSongList: LinearLayout
    private lateinit var llLocal: LinearLayout
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_my, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        llRecent = view.find(R.id.ll_recent)
        llRecent.setOnClickListener {
            val intent = Intent(activity, RecentPlayedActivity::class.java)
            startActivity(intent)
        }
        llCollct = view.find(R.id.ll_collect)
        llCollct.setOnClickListener {
            val intent = Intent(activity, CollectActivity::class.java)
            startActivity(intent)
        }
        llCollectSongList = view.find(R.id.ll_collect_song_list)
        llCollectSongList.setOnClickListener {
            val intent = Intent(activity, CSLActivity::class.java)
            startActivity(intent)
        }
        llLocal = view.find(R.id.ll_song_local)
        llLocal.setOnClickListener {
            val intent = Intent(activity, SongLocalActivity::class.java)
            startActivity(intent)
        }
    }
}