package com.rg.musiound.view.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.rg.musiound.util.dp2px
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt

/**
 * Create by roger
 * on 2019/8/22
 */
class TrangleView : View {
    private val list = mutableListOf<Trangle>()
    private var allTrangleCount = 30
    private val addTrangleOnce = 2
    private val addTrangleInterval = 100
    //刷新时间，不高于30ms，否则明显卡顿
    private val refreshTime = 10
    private val moveSpeed = 0.25
    private val r = dp2px(20)
    private var startTime = System.currentTimeMillis()
    private var mPaintColor = Color.parseColor("#b3dfdb")
    private lateinit var paint: Paint
    private lateinit var path: Path
    private val imageR = dp2px(134)

    private var mIsDrawing = false

    constructor(ctx: Context) : this(ctx, null) {

    }

    constructor(ctx: Context, attrs: AttributeSet?) : this(ctx, attrs, 0) {
    }

    constructor(ctx: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(ctx, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        paint = Paint()
        paint.isAntiAlias = true
        paint.color = mPaintColor
        paint.style = Paint.Style.STROKE
        path = Path()
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val sizeWidth = MeasureSpec.getSize(widthMeasureSpec)
        val sizeHeight = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(sizeWidth, sizeHeight)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawSomething(canvas)
    }

    private fun drawSomething(canvas: Canvas?) {
        val t = System.currentTimeMillis()
        manageTrangle(refreshTime * moveSpeed.toDouble(), canvas)
        if (mIsDrawing) {
            postInvalidateDelayed(max(refreshTime - (System.currentTimeMillis() - t), 0))
        }
    }

    private fun manageTrangle(distance: Double, canvas: Canvas?) {
        val iter = list.iterator()
        while (iter.hasNext()) {
            val trangle = iter.next()
            if (trangle.isOut(0, 0, width, height)) {
                iter.remove()
            } else {
                trangle.move(distance)
            }
        }
        for (x in list) {
            drawTrangle(canvas!!, x)
        }
        if (System.currentTimeMillis() - startTime > addTrangleInterval && list.size < allTrangleCount) {
            for (i in 0..addTrangleOnce) {
                if (list.size < allTrangleCount) {
                    list.add(
                        Trangle(
                            getP(), getP(), getP(),
                            (randD() * 360).toInt(),imageR
                        )
                    )
                }
            }
            startTime = System.currentTimeMillis()
        }

    }

    private fun drawTrangle(canvas: Canvas, trangle: Trangle) {

        paint.strokeWidth = dp2px(2).toFloat()
        paint.alpha = (getAlpha(trangle) * 255).toInt()
//        canvas.drawCircle(trangle.x.toFloat(), bubble.y.toFloat(), bubble.r.toFloat(), paint)
        path.rewind()
        path.moveTo(trangle.p1.x.toFloat(), trangle.p1.y.toFloat())
        path.lineTo(trangle.p2.x.toFloat(), trangle.p2.y.toFloat())
        path.lineTo(trangle.p3.x.toFloat(), trangle.p3.y.toFloat())
        path.close()
        canvas.drawPath(path, paint)
    }

    private fun getAlpha(trangle: Trangle): Double {
        val p = trangle.getCenter()
        val alphaX = 1 - abs(p.x.toDouble() - width / 2 ) / (width / 2)
        val alphaY = 1 - abs(p.y.toDouble() - height / 2) / (height / 2)
        val a = min(alphaX, alphaY)
        Log.d("roger", "${a}")
        return a
    }

    /**
     * 开始扩散
     */
    fun start() {
        mIsDrawing = true
        invalidate()
    }

    /**
     * 停止扩散
     */
    fun stop() {
        mIsDrawing = false
    }

    private fun getP(): Point {
        return Point((width / 2 + randC() * r).toInt(), (height / 2 + randC() * r).toInt())
    }

}

private fun randC(): Double {
    return randD() - 0.5
}

private fun randD(): Double {
    return (0..1000).random().toDouble() / 1000
}