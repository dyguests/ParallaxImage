package com.fanhl.parallaximage

import androidx.core.math.MathUtils


internal fun Float.clamp() = MathUtils.clamp(this, 0f, 1f)