package com.fanhl.parallaximage

import android.content.Context
import android.graphics.Matrix
import android.graphics.RectF
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.widget.ImageView
import java.lang.ref.WeakReference

class ParallaxImageGravitySensorHelper(
    context: Context
) {
    private val sensorManager by lazy { context.getSystemService(Context.SENSOR_SERVICE) as SensorManager }
    private val gravitySensor by lazy { sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) }

    private val gravitySensorEventListener = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        }

        override fun onSensorChanged(event: SensorEvent?) {
            event ?: return
            rotation.apply {
                //这里数值的最大值好像是10
                x = event.values[0] / 10f
                y = event.values[1] / 10f
                z = event.values[2] / 10f
            }
            computeImageMatrix(target?.get() ?: return)

        }
    }

    /** 目前图像 */
    private var target: WeakReference<ImageView>? = null

    /** drawable显示的区域的占比（只计算长轴）（多余部分用来适配重力变化时图像偏移） */
    private var offsetRate = 0.9f

    private var horizontalBias = 0.5f
    private var verticalBias = 0.5f

    /** [x,y,z]方向坐标 */
    private var rotation = Vector3()

    private fun setup(imageView: ImageView) {
        this.target = WeakReference(imageView)

        sensorManager.unregisterListener(gravitySensorEventListener)
        sensorManager.registerListener(gravitySensorEventListener, gravitySensor, SensorManager.SENSOR_DELAY_UI)
    }

    private fun computeImageMatrix(target: ImageView) {
        horizontalBias = ((rotation.x + 10f) / 20).clamp()
        verticalBias = ((rotation.x + 10f) / 20).clamp()

        target?.apply {
            val drawable = drawable ?: return

            val viewWidth = width// - paddingLeft - paddingRight
            val viewHeight = height// - paddingTop - paddingBottom
            val drawableWidth = drawable.intrinsicWidth
            val drawableHeight = drawable.intrinsicHeight

            val rate = maxOf(width.toFloat() / drawableWidth, viewHeight.toFloat() / drawableHeight) * offsetRate

            val drawableRect = RectF(
                drawableWidth / 2f - drawableWidth * rate / 2f,
                drawableHeight / 2f - drawableHeight * rate / 2f,
                drawableHeight / 2f + drawableWidth * rate / 2f,
                drawableWidth / 2f + drawableHeight * rate / 2f
            )

            val viewRect = RectF(0f, 0f, viewWidth.toFloat(), viewHeight.toFloat())

            imageMatrix.setRectToRect(drawableRect, viewRect, Matrix.ScaleToFit.FILL)
            invalidate()
        }
    }

    companion object {
        fun setup(imageView: ImageView) {
            ParallaxImageGravitySensorHelper(imageView.context).setup(imageView)
        }
    }

    /**
     * 三维坐标系用的向量
     */
    private data class Vector3(
        var x: Float = 0f,
        var y: Float = 0f,
        var z: Float = 0f
    )
}