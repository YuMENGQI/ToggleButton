package com.android.byc.togglesbutton

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.LinearInterpolator


/**
 * @author      yu
 * @version     1.0
 * @date        2019/4/24 14:38
 * @description
 */

/**
 * 大概思路:
 * 1.先画开关两边半圆
 * 2.画中间矩形区域
 * 3.画开关圆点
 * 4.点击开关时改变 圆点所处位置(左 和 右)
 */
class ToggleSwitchView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var nWidth: Int = dip2px(45f)
    private var nHeight: Int = dip2px(20f)
    private var radius: Float = dip2px(9f).toFloat()
    private var xPosition: Float = 0.toFloat()
    private var animatorTime: Int = 0
    private var openState = true
    internal var isFocus: Boolean = false
    private var isOn = true
    private  var fraction = 1f
    set(value) {
        field = value
        invalidate()
    }
    var objectAnimator = getAnimator()
    fun toOnState(toOn: Boolean) {
        if((isOn && toOn)  || (!isOn && !toOn)) {
            return
        }
        if (toOn){
            objectAnimator.reverse()
        } else {
            objectAnimator.start()
        }
        isOn = toOn
    }
    fun getAnimator(): ObjectAnimator {
        val oa = ObjectAnimator.ofFloat(this, "fraction", 1f,0f)
        oa.duration = 300
        oa.interpolator = AccelerateDecelerateInterpolator()
        return oa
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(
            MeasureSpec.makeMeasureSpec(nWidth, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(nHeight, MeasureSpec.EXACTLY)
        )
    }

    private fun dip2px(dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paint.color = Color.parseColor("#EEEEEE")
        paint.strokeWidth = dip2px(5f).toFloat()
        paint.strokeCap = Paint.Cap.ROUND
        canvas?.drawLine(dip2px(2.5f).toFloat(), nHeight / 2f, nWidth.toFloat() - dip2px(2.5f).toFloat(), nHeight / 2f, paint)
        paint.color = Color.parseColor("#0F8CFF")
        paint.style = Paint.Style.FILL
        canvas?.drawCircle(dip2px(9f + 27 * fraction).toFloat(), nHeight / 2f,radius, paint )

        paint.color = Color.parseColor("#FFFFFF")
        canvas?.drawCircle(dip2px(9f + 27 * fraction).toFloat(), nHeight / 2f,radius * (1 - fraction), paint )
    }
}