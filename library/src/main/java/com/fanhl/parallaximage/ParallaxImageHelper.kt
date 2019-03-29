package com.fanhl.parallaximage

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

/**
 * 之后把所有视差效果的添加都放到这里
 */
class ParallaxImageHelper {
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

    companion object {
        fun setup(imageView: ImageView) {

        }
    }
}