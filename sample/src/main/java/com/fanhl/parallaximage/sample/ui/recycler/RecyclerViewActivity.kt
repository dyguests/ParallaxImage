package com.fanhl.parallaximage.sample.ui.recycler

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fanhl.parallaximage.sample.R
import com.fanhl.parallaximage.sample.ui.recycler.adapter.RecyclerAdapter
import com.fanhl.parallaximage.sample.model.Cover
import kotlinx.android.synthetic.main.activity_recycler_view.*

class RecyclerViewActivity : AppCompatActivity() {
    private val adapter by lazy { RecyclerAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)
        initData()
        refreshData()
    }

    private fun initData() {
        adapter.bindToRecyclerView(recycler_view)
    }

    private fun refreshData() {
        adapter.setNewData(
            List(20) { Cover() }
        )
    }
}
