package com.rg.musiound.view.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.rg.musiound.R
import com.rg.musiound.base.BaseFragment
import com.rg.musiound.bean.MV
import com.rg.musiound.interfaces.model.IFragmentMusicRankModel
import com.rg.musiound.interfaces.presenter.IFragmentMusicRankPresenter
import com.rg.musiound.interfaces.view.IFragmentMusicRankView
import com.rg.musiound.presenter.FragmentMusicRankPresenter
import com.rg.musiound.util.OnItemClickListener
import com.rg.musiound.view.activity.MVPlayActivity
import com.rg.musiound.view.adapter.MVListAdapter
import kotlinx.android.synthetic.main.fragment_music_rank.*

/**
 * Create by roger
 * on 2019/8/18
 */
class MusicRankFragment: BaseFragment<IFragmentMusicRankView, IFragmentMusicRankPresenter, IFragmentMusicRankModel>(), IFragmentMusicRankView {


    override fun onCreateView(view: View, savedInstanceState: Bundle?) {
        presenter?.start()

    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_music_rank
    }

    override fun getViewToAttach(): IFragmentMusicRankView {
        return this
    }

    override fun createPresenter(): IFragmentMusicRankPresenter {
        return FragmentMusicRankPresenter()
    }

    override fun setVideoList(videos: List<MV>) {
        val adapter = MVListAdapter(videos, activity as Context)
        rv_fragment_mv_rank.layoutManager = LinearLayoutManager(activity)
        rv_fragment_mv_rank.adapter = adapter
        adapter.setOnItemClickListener(object: OnItemClickListener {
            override fun onItemClick(position: Int) {
                val id = videos[position].id
                val intent = Intent(activity, MVPlayActivity::class.java)
                intent.putExtra("id", id)
                startActivity(intent)
            }

        })
    }

    override fun showError() {
    }
}