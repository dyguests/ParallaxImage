package com.fanhl.windowimageview.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val url = "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f5/Ophrys_apifera_flower1.jpg/170px-Ophrys_apifera_flower1.jpg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = LayoutInflater.from(this).inflate(R.layout.activity_main, null)
        setContentView(root)

        Picasso.get()
                .load(url)
                .into(img_a)
        Picasso.get()
                .load(url)
                .into(img_b)
        Picasso.get()
                .load(url)
                .into(img_c)
    }
}
