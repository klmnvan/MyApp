package com.example.myapplication.screens

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.objects.UserData
import com.example.myapplication.databinding.ActivityMapKitBinding
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition

class MapKit : AppCompatActivity() {
    private lateinit var binding: ActivityMapKitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            initialize("c68f210a-46cb-4959-940c-0e5130d712db")
            Log.d("Map", "Success")
        } catch (e: Exception){
            Log.d("Map", e.message.toString())
        }
        MapKitFactory.initialize(this)
        binding = ActivityMapKitBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.mapview.mapWindow.map.move(
            CameraPosition(
                Point(56.336400111291745,43.99270419079589),
                /* zoom, приближение = */ 15.0f,
                /* azimuth, поворот = */ 0.0f,
                /* tilt, наклон= */ 0.0f
            )
        )
    }

    private fun initialize(apiKey: String) {
        if (!UserData.mapIsInit) {
            MapKitFactory.setApiKey(apiKey)
            MapKitFactory.initialize(this@MapKit)
            UserData.mapIsInit = true
        }
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        binding.mapview.onStart()
    }

    override fun onStop() {
        binding.mapview.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }


}