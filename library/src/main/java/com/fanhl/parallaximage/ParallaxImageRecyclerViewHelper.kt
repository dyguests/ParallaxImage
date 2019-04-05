package com.fanhl.parallaximage

import android.graphics.Matrix
import android.graphics.RectF
import android.util.Log
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import java.lang.ref.WeakReference

/**
 * 处理图像在滚动中的显示
 */
class ParallaxImageRecyclerViewHelper private constructor() {
    /** 目前图像 */
    private var target: WeakReference<ImageView>? = null
    /** 目前图像所在的滚动父布局 */
    private var dependency: WeakReference<RecyclerView>? = null

    /** target的宽度与dependency的宽度的比率 */
    private var widthRate = 1f
    private var heightRate = 1f

    private var horizontalBias = 0.5f
    private var verticalBias = 0.5f

    /**
     * 临时存放区
     * FIXME 之后加上生命周期
     */
    private val outLocation = IntArray(2)

    var targetX: Int = 0
    var targetY: Int = 0
    var dependencyX: Int = 0
    var dependencyY: Int = 0

    var drawableRect: RectF = RectF()
    var viewRect: RectF = RectF()

    private val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            computeImageMatrix(
                target?.get() ?: return,
                dependency?.get() ?: return
            )
        }
    }

    private fun setup(recyclerView: RecyclerView, imageView: ImageView) {
        this.target = WeakReference(imageView)
        if (this.dependency?.get() != recyclerView) {
            this.dependency?.get()?.removeOnScrollListener(onScrollListener)
            this.dependency = WeakReference(recyclerView)
        }

        recyclerView?.addOnScrollListener(onScrollListener) ?: return
    }

    private fun computeImageMatrix(
        target: ImageView,
        dependency: RecyclerView
    ) {
        //FIXME 这部分可以只修改一次
        widthRate = target.width.toFloat() / dependency.width
        heightRate = target.height.toFloat() / dependency.height

        //FIXME 这部分可以根据父布局的滚动方向，只计算对应方向的就可以了
        target.getLocationOnScreen(outLocation)
        targetX = outLocation[0]
        targetY = outLocation[1]
        dependency.getLocationOnScreen(outLocation)
        dependencyX = outLocation[0]
        dependencyY = outLocation[1]

        horizontalBias = ((targetX - dependencyX).toFloat() / (dependency.width - target.width)).clamp()
        verticalBias = ((targetY - dependencyY).toFloat() / (dependency.height - target.height)).clamp()

//        Log.d(TAG, "widthRate:$widthRate,heightRate:$heightRate  horizontalBias:$horizontalBias,verticalBias:$verticalBias")

        target?.apply {
            val drawable = drawable ?: return

//            val matrix = imageMatrix

            val viewWidth = width// - paddingLeft - paddingRight
            val viewHeight = height// - paddingTop - paddingBottom
            val drawableWidth = drawable.intrinsicWidth
            val drawableHeight = drawable.intrinsicHeight

            // TODO 以下计算式等其它部分全部完全后再优化

            if (drawableWidth * viewHeight > drawableHeight * viewWidth) {
                val scale = viewHeight.toFloat() / drawableHeight.toFloat()
                //                val horizontalBias = left.toFloat() / (left + (parent as View).right - right)
                val dx = horizontalBias * (drawableWidth - viewWidth / scale)

                drawableRect.apply {
                    left = dx
                    top = 0f
                    right = dx + drawableHeight.toFloat()
                    bottom = drawableHeight.toFloat() * viewWidth / viewHeight
                }
//                RectF(dx, 0f, dx + drawableHeight.toFloat(), drawableHeight.toFloat() * viewWidth / viewHeight)
            } else {
                val scale = viewWidth.toFloat() / drawableWidth.toFloat()
                //                val verticalBias = top.toFloat() / (top + (parent as View).bottom - bottom)
                val dy = verticalBias * (drawableHeight - viewHeight / scale)

                drawableRect.apply {
                    left = 0f
                    top = dy
                    right = drawableWidth.toFloat()
                    bottom = dy + drawableWidth.toFloat() * viewHeight / viewWidth
                }
//                RectF(0f, dy, drawableWidth.toFloat(), dy + drawableWidth.toFloat() * viewHeight / viewWidth)
            }

            viewRect = RectF(0f, 0f, viewWidth.toFloat(), viewHeight.toFloat())

            Log.d(TAG, "drawableRect:$drawableRect  viewRect:$viewRect")

            imageMatrix.setRectToRect(drawableRect, viewRect, Matrix.ScaleToFit.FILL)
            invalidate()

//            imageMatrix = matrix
        }
    }

    companion object {
        private val TAG = ParallaxImageRecyclerViewHelper::class.java.simpleName

        fun setup(imageView: ImageView, recyclerView: RecyclerView) {
            ParallaxImageRecyclerViewHelper().setup(recyclerView, imageView)
        }
    }
}
