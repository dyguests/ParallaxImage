package com.fanhl.parallaximage

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class ParallaxImageScrollViewHelper {
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
}