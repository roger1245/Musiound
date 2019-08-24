package com.rg.musiound.view.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.EditText
import com.rg.musiound.util.OnItemClickListener

/**
 * Create by roger
 * on 2019/8/24
 */
const val DRAWABLE_LEFT = 0
const val DRAWABLE_TOP = 1
const val DRAWABLE_RIGHT = 2
const val DRAWABLE_BOTTOM = 3
class DropableEditText : EditText {


    private var endListener: OnEditEndClickListener? = null

    constructor(ctx: Context) : this(ctx, null) {

    }

    constructor(ctx: Context, attrs: AttributeSet?) : this(ctx, attrs, 0) {
    }

    constructor(ctx: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(ctx, attrs, defStyleAttr) {
    }

    fun setDropEndClickListener(listener: OnEditEndClickListener) {
        endListener = listener
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            if (it.action == MotionEvent.ACTION_UP) {
                val end = compoundDrawables[DRAWABLE_RIGHT]
                if (event.x >= width - paddingRight - end.intrinsicWidth) {
                    endListener?.let {
                        it.onClick()
                    }
                }
            }
        }
        return super.onTouchEvent(event)
    }
}
interface OnEditEndClickListener{
    fun onClick()
}