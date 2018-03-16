package com.fanhl.windowimageview

import android.content.Context
import android.util.AttributeSet

/**
 * desc: 橱窗ImageView
 * date: 2018/3/16
 *
 * @author fanhl
 */
class WindowImageView : android.support.v7.widget.AppCompatImageView {
    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    override fun setFrame(l: Int, t: Int, r: Int, b: Int): Boolean {
        if (drawable != null) {
            val matrix = imageMatrix

            val scale: Float
            val viewWidth = width - paddingLeft - paddingRight
            val viewHeight = height - paddingTop - paddingBottom
            val drawableWidth = drawable.intrinsicWidth
            val drawableHeight = drawable.intrinsicHeight

            scale = if (drawableWidth * viewHeight > drawableHeight * viewWidth) {
                viewHeight.toFloat() / drawableHeight.toFloat()
            } else {
                viewWidth.toFloat() / drawableWidth.toFloat()
            }

            matrix.setScale(scale, scale)
            imageMatrix = matrix
        }
        return super.setFrame(l, t, r, b)
    }
}
