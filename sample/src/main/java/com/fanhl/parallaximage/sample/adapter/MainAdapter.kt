package com.fanhl.parallaximage.sample.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.fanhl.parallaximage.sample.R
import com.fanhl.parallaximage.sample.model.Cover
import com.fanhl.parallaximage.sample.setImage
import kotlinx.android.synthetic.main.item_main_cover.view.*

class MainAdapter : BaseQuickAdapter<Cover, BaseViewHolder>(R.layout.item_main_cover) {
    override fun convert(helper: BaseViewHolder?, item: Cover?) {
        helper?.itemView?.apply {
            img_cover.setImage(
                "https://csdnimg.cn/pubfooter/images/csdn-zx.png",
                R.drawable.ic_launcher_foreground
            )
        }
    }
}