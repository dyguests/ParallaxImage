package com.fanhl.windowimageview

import android.content.Context
import android.graphics.Matrix
import android.graphics.RectF
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewParent
import android.widget.ImageView
import android.widget.ScrollView

/**
 * desc: 橱窗ImageView
 * date: 2018/3/16
 *
 * @author fanhl
 */
class WindowImageView : android.support.v7.widget.AppCompatImageView {
    /** TAG */
    private val TAG = WindowImageView::class.java.simpleName!!

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        scaleType = ImageView.ScaleType.MATRIX
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        var scrollableView: ViewParent? = parent
        while (scrollableView != null && scrollableView !is ScrollView) {
            scrollableView = scrollableView.parent
        }
        if (scrollableView == null) {
            return
        }

        val scrollView = scrollableView as ScrollView
        scrollView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            recomputeImgMatrix()
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        recomputeImgMatrix()
    }

    override fun setFrame(l: Int, t: Int, r: Int, b: Int): Boolean {
        recomputeImgMatrix()
        return super.setFrame(l, t, r, b)
    }

    private fun recomputeImgMatrix() {
        Log.i(TAG, "recomputeImgMatrix ")
        val drawable = drawable ?: return

        val matrix = imageMatrix

        val viewWidth = width - paddingLeft - paddingRight
        val viewHeight = height - paddingTop - paddingBottom
        val drawableWidth = drawable.intrinsicWidth
        val drawableHeight = drawable.intrinsicHeight

        // TODO 以下计算式等其它部分全部完全后再优化

        val drawableRect = if (drawableWidth * viewHeight > drawableHeight * viewWidth) {
            val scale = viewHeight.toFloat() / drawableHeight.toFloat()
            val horizontalBias = left.toFloat() / (left + (parent as View).right - right)
            val dx = horizontalBias * (drawableWidth - viewWidth / scale)
            RectF(dx, 0f, dx + drawableHeight.toFloat(), drawableHeight.toFloat() * viewWidth / viewHeight)
        } else {
            val scale = viewWidth.toFloat() / drawableWidth.toFloat()
            val verticalBias = top.toFloat() / (top + (parent as View).bottom - bottom)
            val dy = verticalBias * (drawableHeight - viewHeight / scale)
            RectF(0f, dy, drawableWidth.toFloat(), dy + drawableWidth.toFloat() * viewHeight / viewWidth)
        }

        val viewRect = RectF(0f, 0f, viewWidth.toFloat(), viewHeight.toFloat())

        matrix.setRectToRect(drawableRect, viewRect, Matrix.ScaleToFit.FILL)

        imageMatrix = matrix
    }
}
