package com.example.myapplication.screens

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

class Rotation : AppCompatActivity() {
    lateinit var sManager: SensorManager
    lateinit var sListener: SensorEventListener
    lateinit var s1: Sensor
    lateinit var s2: Sensor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rotation)
        start()
    }

    fun start(){
        var inR = FloatArray(9)
        var l = FloatArray(9)
        var accs = FloatArray(3)
        var magf = FloatArray(3)
        var v = FloatArray(3)

        sManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        s1 = sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)!!
        s2 = sManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)!!

        sListener = object : SensorEventListener{
            override fun onSensorChanged(event: SensorEvent?) {
                when(event!!.sensor.type){
                    Sensor.TYPE_ACCELEROMETER -> accs = event.values.clone()
                    Sensor.TYPE_MAGNETIC_FIELD -> magf = event.values.clone()
                }
                SensorManager.getRotationMatrix(inR, l, accs, magf)
                val outR = FloatArray(9)
                SensorManager.remapCoordinateSystem(inR, SensorManager.AXIS_X, SensorManager.AXIS_Z, outR)
                SensorManager.getOrientation(outR, v)
                val degree = v[2] * 57.2958f
                Log.d("Angle", degree.toString())
            }

            override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
            }

        }
        sManager.registerListener(sListener, s1, SensorManager.SENSOR_DELAY_UI)
        sManager.registerListener(sListener, s2, SensorManager.SENSOR_DELAY_UI)
    }

}