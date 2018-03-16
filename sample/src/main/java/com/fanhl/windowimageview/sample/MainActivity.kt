package com.fanhl.windowimageview.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    val url = "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f5/Ophrys_apifera_flower1.jpg/170px-Ophrys_apifera_flower1.jpg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
