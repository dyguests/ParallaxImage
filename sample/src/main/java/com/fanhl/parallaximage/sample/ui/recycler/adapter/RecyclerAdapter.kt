package com.fanhl.parallaximage.sample.ui.recycler.adapter

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.fanhl.parallaximage.ParallaxImageRecyclerViewHelper
import com.fanhl.parallaximage.sample.R
import com.fanhl.parallaximage.sample.model.Cover
import kotlinx.android.synthetic.main.item_main_cover.view.*

class RecyclerAdapter : BaseQuickAdapter<Cover, RecyclerAdapter.ViewHolder>(R.layout.item_main_cover) {
    private val imgReses = arrayOf(
        R.drawable.img_test,
        R.drawable.img_test2,
        R.drawable.img_test3,
        R.drawable.img_test4,
        R.drawable.img_test5,
        R.drawable.img_test6,
        R.drawable.img_test7
    )

    override fun convert(helper: ViewHolder?, item: Cover?) {
        helper?.apply {
            itemView?.apply {
                img_cover.setImageResource(imgReses[adapterPosition % 7])
//                img_cover.setImageAsDrawable(
//                    "http://img1.imgtn.bdimg.com/it/u=1681846736,3552678303&fm=26&gp=0.jpg",
//                    R.drawable.ic_launcher_foreground
//                )
            }
        }
    }

    inner class ViewHolder(itemView: View) : BaseViewHolder(itemView) {
        init {
            itemView.apply {
                ParallaxImageRecyclerViewHelper.setup(recyclerView, img_cover)
            }
        }
    }
}