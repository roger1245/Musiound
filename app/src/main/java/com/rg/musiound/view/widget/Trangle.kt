package com.rg.musiound.view.widget

import android.graphics.Point
import com.rg.musiound.util.dp2px
import kotlin.math.cos
import kotlin.math.sin

/**
 * Create by roger
 * on 2019/8/22
 */
class Trangle(val p1: Point, val p2 : Point, val p3: Point, val angle: Int, val imageR: Int) {
    init {
        move((imageR - dp2px(20)).toDouble())
    }

    fun move(distance: Double) {
        p1.apply {
            val xDelta = cos(Math.toRadians(angle.toDouble())) * distance
            val yDelta = sin(Math.toRadians(angle.toDouble())) * distance
            x += xDelta.toInt()
            y -= yDelta.toInt()
        }
        p2.apply {
            val xDelta = cos(Math.toRadians(angle.toDouble())) * distance
            val yDelta = sin(Math.toRadians(angle.toDouble())) * distance
            x += xDelta.toInt()
            y -= yDelta.toInt()
        }
        p3.apply {
            val xDelta = cos(Math.toRadians(angle.toDouble())) * distance
            val yDelta = sin(Math.toRadians(angle.toDouble())) * distance
            x += xDelta.toInt()
            y -= yDelta.toInt()
        }

    }

    fun isOut(cl: Int, ct: Int, cr: Int, cb: Int): Boolean {
        p1.apply {
            if (x < cl || x > cr || y < ct || y > cb) {
                return true
            }
        }
        p2.apply {
            if (x < cl || x > cr || y < ct || y > cb) {
                return true
            }
        }
        p3.apply {
            if (x < cl || x > cr || y < ct || y > cb) {
                return true
            }
        }

        return false
    }
    fun getCenter(): Point {
        val x = (p1.x + p2.x + p3.x) / 3
        val y = (p1.y + p2.y + p3.y) / 3
        return Point(x, y)
    }
}