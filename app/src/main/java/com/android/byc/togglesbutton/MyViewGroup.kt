package com.android.byc.togglesbutton

import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator



/**
 * @author      yu
 * @version     1.0
 * @date        2019/4/25 15:21
 * @description
 */
class MyViewGroup @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    private val oval = OvalButton(context)
    private var buttonColor : Int

    init {
        var array = context.obtainStyledAttributes(attrs, R.styleable.MyViewGroup)
        buttonColor = array.getColor(R.styleable.MyViewGroup_buttonColor, 1)
        array.recycle()
        removeAllViews()
        addView(oval)
    }

    private val paddingWidth = dip2px(2.5f)
    private var isOn = true
    private val radius = dip2px(9f).toFloat()
    private var pointWidth: Int = dip2px(50f)
    private var pointHeight: Int = dip2px(25f)
    private var fraction = 1f
    set(value) {
        field = value
        requestLayout()
        oval.invalidate()
    }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    var objectAnimator = getAnimator()
    companion object {
        fun dip2px(dpValue: Float): Int {
            val scale = Resources.getSystem().displayMetrics.density
            return (dpValue * scale + 0.5f).toInt()
        }
    }

    override fun shouldDelayChildPressedState(): Boolean {
        return false
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        oval.measure(widthMeasureSpec, heightMeasureSpec)

        setMeasuredDimension(
            MeasureSpec.makeMeasureSpec(pointWidth, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(pointHeight, MeasureSpec.EXACTLY)
        )
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        oval.layout(
            (fraction * dip2px(27f)).toInt() + paddingWidth + 1,
            (height / 2 - radius).toInt(),
            (fraction * dip2px(27f) + 2 * radius).toInt() + paddingWidth,
            (height / 2 + radius).toInt()
        )
    }

    override fun dispatchDraw(canvas: Canvas?) {
        paint.color = Color.parseColor("#EEEEEE")
        paint.strokeWidth = dip2px(5f).toFloat()
        paint.strokeCap = Paint.Cap.ROUND
        canvas?.drawLine(
            dip2px(2.5f).toFloat() + paddingWidth,
            pointHeight / 2f,
            pointWidth.toFloat() - dip2px(2.5f).toFloat() - paddingWidth,
            pointHeight / 2f,
            paint
        )

        super.dispatchDraw(canvas)
    }

    fun getAnimator(): ObjectAnimator {
        val oa = ObjectAnimator.ofFloat(this, "fraction", 1f,0f)
        oa.duration = 300
        oa.interpolator = AccelerateDecelerateInterpolator()
        return oa
    }

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

    private inner class OvalButton @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
    ) : View(context, attrs, defStyleAttr) {

        init {
            elevation = dip2px(2f).toFloat()
            val ovalShape = OvalShape()
            background = ShapeDrawable(ovalShape)
        }

        override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
            setMeasuredDimension(
                MeasureSpec.makeMeasureSpec(radius.toInt(), MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(radius.toInt(), MeasureSpec.EXACTLY)
            )
        }

        override fun onDraw(canvas: Canvas?) {
            super.onDraw(canvas)
            paint.color = buttonColor
            paint.style = Paint.Style.FILL
            canvas?.drawCircle(width / 2f, height / 2f, radius -0.25f, paint)

            paint.color = Color.parseColor("#FFFFFF")
            canvas?.drawCircle(width / 2f, height / 2f,radius * (1 - fraction), paint)
        }
    }
}