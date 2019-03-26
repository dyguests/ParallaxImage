package com.fanhl.parallaximage

import android.graphics.Matrix
import android.graphics.RectF
import android.view.View
import android.widget.ImageView
import androidx.core.util.Pools
import androidx.recyclerview.widget.RecyclerView

class ParallaxImageRecyclerViewHelper private constructor() {
    private var imageView: ImageView? = null
    private var recyclerView: RecyclerView? = null

    private val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            computeImageMatrix()
        }
    }

    private fun setup(imageView: ImageView) {
        val recyclerView = findRecyclerViewParent(imageView)

        this.imageView = imageView
        if (this.recyclerView != recyclerView) {
            this.recyclerView?.removeOnScrollListener(onScrollListener)
            this.recyclerView = recyclerView
        }
        recyclerView ?: return
        recyclerView.addOnScrollListener(onScrollListener)
    }

    private fun findRecyclerViewParent(imageView: ImageView): RecyclerView? {
        var parent = imageView.parent
        while (parent != null) {
            if (parent is RecyclerView) {
                return parent
            }
            parent = parent.parent
        }
        return null
    }

    private fun computeImageMatrix() {
        imageView?.apply {
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

    companion object {
        private val helperPools by lazy { Pools.SimplePool<ParallaxImageRecyclerViewHelper>(12) }

        fun setup(imageView: ImageView) {
            val helper = helperPools.acquire() ?: ParallaxImageRecyclerViewHelper()
            helper.setup(imageView)
        }
    }
}