package com.fanhl.parallaximage.sample.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.fanhl.parallaximage.ParallaxImageRecyclerViewHelper
import com.fanhl.parallaximage.sample.R
import com.fanhl.parallaximage.sample.model.Cover
import com.fanhl.parallaximage.sample.setImage
import kotlinx.android.synthetic.main.item_main_cover.view.*

class MainAdapter : BaseQuickAdapter<Cover, BaseViewHolder>(R.layout.item_main_cover) {
    override fun convert(helper: BaseViewHolder?, item: Cover?) {
        helper?.itemView?.apply {
            ParallaxImageRecyclerViewHelper.setup(img_cover)
            img_cover.setImage(
                "http://img1.imgtn.bdimg.com/it/u=1681846736,3552678303&fm=26&gp=0.jpg",
                R.drawable.ic_launcher_foreground
            )
        }
    }
}