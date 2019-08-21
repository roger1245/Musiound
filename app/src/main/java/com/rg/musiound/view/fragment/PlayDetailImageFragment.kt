package com.rg.musiound.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rg.musiound.R
import com.rg.musiound.util.extensions.setImageFromUrl
import kotlinx.android.synthetic.main.fragment_play_detail_image.*

/**
 * Create by roger
 * on 2019/8/21
 */
class PlayDetailImageFragment : Fragment() {
    private lateinit var imgUrl: String
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_play_detail_image, container, false)
        imgUrl = arguments?.getString("imgUrl") as String
        Log.d("roger", imgUrl)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        Log.d("roger", imgUrl)
        iv_play_detail_image.setImageFromUrl(imgUrl)
    }
}