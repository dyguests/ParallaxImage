package com.fanhl.parallaximage.sample.ui.gravity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fanhl.parallaximage.ParallaxImageGravitySensorHelper
import com.fanhl.parallaximage.sample.R
import kotlinx.android.synthetic.main.activity_gravity.*

class GravityActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gravity)
        ParallaxImageGravitySensorHelper.setup(img_cover1)
        ParallaxImageGravitySensorHelper.setup(img_cover2)
        ParallaxImageGravitySensorHelper.setup(img_cover3)
    }
}
