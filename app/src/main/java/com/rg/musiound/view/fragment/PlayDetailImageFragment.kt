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
import com.rg.musiound.bean.Song
import com.rg.musiound.service.PlayManager
import com.rg.musiound.service.PlayService
import com.rg.musiound.service.ruler.Rulers
import com.rg.musiound.util.extensions.setImageFromUrl
import com.rg.musiound.view.widget.PlayStateChangeEvent
import com.rg.musiound.view.widget.TrangleView
import kotlinx.android.synthetic.main.fragment_play_detail_image.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.find

/**
 * Create by roger
 * on 2019/8/21
 */
class PlayDetailImageFragment : Fragment() {

    private lateinit var imgUrl: String
    private lateinit var iv: ImageView
    private lateinit var trangleView: TrangleView
    private lateinit var objectAnimator: ObjectAnimator
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_play_detail_image, container, false)
        imgUrl = arguments?.getString("imgUrl") as String
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iv = view.find(R.id.iv_play_detail_image)
        iv_play_detail_image.setImageFromUrl(imgUrl)
        trangleView = view.find(R.id.trangle_view)
        clip()
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    override fun onResume() {
        super.onResume()
        if (PlayManager.instance.isPlaying) {
            trangleView.start()
            if (objectAnimator.isPaused) {
                objectAnimator.resume()
            } else {
                objectAnimator.start()
            }
        }

    }

    override fun onPause() {
        trangleView.stop()
        objectAnimator.pause()
        super.onPause()
    }

    companion object {
        fun newInstance(pic: String): PlayDetailImageFragment {
            val fragment = PlayDetailImageFragment()
            val bundle = Bundle()
            bundle.putString("imgUrl", pic)
            fragment.arguments = bundle
            return fragment
        }
    }

    private fun clip() {
        Glide.with(this).load(imgUrl).apply(RequestOptions.circleCropTransform()).into(iv_play_detail_image)


        //属性动画让roundImage旋转起来
        objectAnimator = ObjectAnimator.ofFloat(iv, "rotation", 0F, 360F)
        objectAnimator.setDuration(15000)
        objectAnimator.setRepeatMode(ValueAnimator.RESTART)
        objectAnimator.setInterpolator(LinearInterpolator())
        objectAnimator.setRepeatCount(-1)

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: PlayStateChangeEvent) {
        val state = event.getMessage()
        when (state) {
            PlayService.STATE_INITIALIZED -> {
            }
            PlayService.STATE_STARTED -> {
                trangleView.start()
                if (objectAnimator.isPaused) {
                    objectAnimator.resume()
                } else {
                    objectAnimator.start()
                }

            }
            PlayService.STATE_PAUSED -> {
                trangleView.stop()
                objectAnimator.pause()

            }

            PlayService.STATE_COMPLETED -> {

            }
            PlayService.STATE_STOPPED -> {

            }
            PlayService.STATE_RELEASED -> {
            }
            PlayService.STATE_ERROR -> {
            }
        }
    }
}
