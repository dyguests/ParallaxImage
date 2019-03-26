package com.fanhl.parallaximage

import android.widget.ImageView
import androidx.core.util.Pools
import androidx.recyclerview.widget.RecyclerView

class ParallaxImageRecyclerViewHelper private constructor() {

    private fun setup(imageView: ImageView) {
        val recyclerView = findRecyclerViewParent(imageView)

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

    companion object {
        private val helperPools by lazy { Pools.SimplePool<ParallaxImageRecyclerViewHelper>(12) }

        fun setup(imageView: ImageView) {
            val helper = helperPools.acquire() ?: ParallaxImageRecyclerViewHelper()
            helper.setup(imageView)
        }
    }
}