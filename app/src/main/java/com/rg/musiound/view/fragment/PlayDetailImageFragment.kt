package com.rg.musiound.view.fragment

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.Outline
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rg.musiound.R
import com.rg.musiound.util.extensions.setImageFromUrl
import com.rg.musiound.view.widget.TrangleView
import kotlinx.android.synthetic.main.fragment_play_detail_image.*
import org.jetbrains.anko.find

/**
 * Create by roger
 * on 2019/8/21
 */
class PlayDetailImageFragment : Fragment() {
    private lateinit var imgUrl: String
    private lateinit var iv: ImageView
    private lateinit var trangleView: TrangleView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_play_detail_image, container, false)
        imgUrl = arguments?.getString("imgUrl") as String
        Log.d("roger", imgUrl)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iv = view.find(R.id.iv_play_detail_image)
        iv_play_detail_image.setImageFromUrl(imgUrl)
        trangleView = view.find(R.id.trangle_view)
        clip()
    }

    override fun onResume() {
        super.onResume()
        trangleView.start()

    }

    override fun onPause() {
        trangleView.stop()
        super.onPause()
    }

    companion object {
        fun newInstance(pic : String) : PlayDetailImageFragment {
            val fragment = PlayDetailImageFragment()
            val bundle = Bundle()
            bundle.putString("imgUrl", pic)
            fragment.arguments = bundle
            return fragment
        }
    }

    private fun clip() {
        Glide.with(this).load(imgUrl).apply(RequestOptions.circleCropTransform()).into(iv_play_detail_image)

        //图片裁剪和旋转
//        if (Build.VERSION.SDK_INT >= 21) {
//            //裁剪
//            iv.setOutlineProvider(object : ViewOutlineProvider() {
//                override fun getOutline(view: View, outline: Outline) {
//                    val width = iv.getWidth()
//                    val height = iv.getHeight()
//                    outline.setOval(0, 0, width, height)
//                }
//            })
//            iv.setClipToOutline(true)

            //属性动画让roundImage旋转起来
            val objectAnimator = ObjectAnimator.ofFloat(iv, "rotation", 0F, 360F)
            objectAnimator.setDuration(15000)
            objectAnimator.setRepeatMode(ValueAnimator.RESTART)
            objectAnimator.setInterpolator(LinearInterpolator())
            objectAnimator.setRepeatCount(-1)
            objectAnimator.start()
//        }
    }
}