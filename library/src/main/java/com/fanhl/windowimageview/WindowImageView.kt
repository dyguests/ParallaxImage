package com.fanhl.windowimageview

import android.content.Context
import android.graphics.Matrix
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView

/**
 * desc: 橱窗ImageView
 * date: 2018/3/16
 *
 * @author fanhl
 */
class WindowImageView : android.support.v7.widget.AppCompatImageView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        scaleType = ImageView.ScaleType.MATRIX
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
        val drawable = drawable ?: return

        val matrix = imageMatrix

        val viewWidth = width - paddingLeft - paddingRight
        val viewHeight = height - paddingTop - paddingBottom
        val drawableWidth = drawable.intrinsicWidth
        val drawableHeight = drawable.intrinsicHeight

        val scale = if (drawableWidth * viewHeight > drawableHeight * viewWidth) {
            viewHeight.toFloat() / drawableHeight.toFloat()
        } else {
            viewWidth.toFloat() / drawableWidth.toFloat()
        }

//        matrix.setScale(scale, scale)

        val drawableScaledWidth = drawableWidth * scale
        val drawableScaledHeight = drawableHeight * scale

        val horizontalBias = left.toFloat() / (left + (parent as View).right - right)
        val verticalBias = top.toFloat() / (top + (parent as View).bottom - bottom)

        val dx = if (drawableScaledWidth > viewWidth) {
            horizontalBias * (drawableWidth - viewWidth / scale)
        } else {
            0f
        }
        val dy = if (drawableScaledHeight > viewHeight) {
            verticalBias * (drawableHeight - viewHeight / scale)
        } else {
            0f
        }

//        matrix.setTranslate(dx, dy)

        val drawableRect: RectF
        if (drawableScaledWidth > viewWidth) {
            drawableRect = RectF(dx, 0f, dx + drawableHeight.toFloat(), drawableHeight.toFloat())

        } else {
            drawableRect = RectF(0f, dy, drawableWidth.toFloat(), dy + drawableWidth.toFloat())

        }

        val viewRect = RectF(0f, 0f, viewWidth.toFloat(), viewHeight.toFloat())

        matrix.setRectToRect(drawableRect, viewRect, Matrix.ScaleToFit.FILL)

        imageMatrix = matrix
    }
}
