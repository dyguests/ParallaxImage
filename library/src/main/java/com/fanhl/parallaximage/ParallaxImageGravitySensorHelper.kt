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

    /** target的宽度与dependency的宽度的比率 */
    private var widthRate = 0.9f
    private var heightRate = 0.9f

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

            val drawableRect = if (drawableWidth * viewHeight > drawableHeight * viewWidth) {
                val scale = viewHeight.toFloat() / drawableHeight.toFloat()
//                val horizontalBias = left.toFloat() / (left + (parent as View).right - right)
                val dx = horizontalBias * (drawableWidth - viewWidth / scale)
                RectF(dx, 0f, dx + drawableHeight.toFloat(), drawableHeight.toFloat() * viewWidth / viewHeight)
            } else {
                val scale = viewWidth.toFloat() / drawableWidth.toFloat()
//                val verticalBias = top.toFloat() / (top + (parent as View).bottom - bottom)
                val dy = verticalBias * (drawableHeight - viewHeight / scale)
                RectF(0f, dy, drawableWidth.toFloat(), dy + drawableWidth.toFloat() * viewHeight / viewWidth)
            }

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