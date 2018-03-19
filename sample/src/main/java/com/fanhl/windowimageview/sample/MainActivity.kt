package com.fanhl.windowimageview.sample

import android.os.Build
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.v7.app.AppCompatActivity
import android.transition.TransitionManager
import android.view.LayoutInflater
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val url = "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f5/Ophrys_apifera_flower1.jpg/170px-Ophrys_apifera_flower1.jpg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = LayoutInflater.from(this).inflate(R.layout.activity_main, null) as ConstraintLayout
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


        var set = false
        val constraint1 = ConstraintSet()
        constraint1.clone(root)
        val constraint2 = ConstraintSet()
        constraint2.clone(this, R.layout.activity_main_alt)

        fab.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                TransitionManager.beginDelayedTransition(root)
                val constraint = if (set) constraint1 else constraint2
                constraint.applyTo(root)
                set = !set
            }
        }
    }
}
