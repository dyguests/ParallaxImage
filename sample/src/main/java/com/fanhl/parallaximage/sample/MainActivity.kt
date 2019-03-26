package com.fanhl.parallaximage.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fanhl.parallaximage.sample.adapter.MainAdapter
import com.fanhl.parallaximage.sample.model.Cover
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val adapter by lazy { MainAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initData()
        refreshData()
    }

    private fun initData() {
        recycler_view.adapter = adapter
    }

    private fun refreshData() {
        adapter.setNewData(
            List(20) { Cover() }
        )
    }
}
