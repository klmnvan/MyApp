package com.example.myapplication.screens

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.myapplication.databinding.ActivityGeolocationBinding
import java.security.Provider
import java.util.Locale

/* проверяем разрешения
* запрашиваем, если нет, если есть, запускаем получение локации
* проверяем, включен ли gps
* если нет, просим включить */
class Geolocation : AppCompatActivity() {
    private lateinit var binding: ActivityGeolocationBinding
    lateinit var locationManager: LocationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGeolocationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        verifyPremission()
    }

    fun verifyPremission(){
        val p1 = ActivityCompat.checkSelfPermission(this@Geolocation, Manifest.permission.ACCESS_COARSE_LOCATION)
        val p2 = ActivityCompat.checkSelfPermission(this@Geolocation, Manifest.permission.ACCESS_FINE_LOCATION)
        val gr = PackageManager.PERMISSION_GRANTED
        if(p1 == gr && p2 == gr){
            getLocation()
        } else {
            val ps = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
            ActivityCompat.requestPermissions(this@Geolocation, ps, 0)
        }
    }

    @SuppressLint("MissingPermission")
    fun getLocation(){
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        try {
            gpsIsEnable()
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0L, 0f, locationListener)
        } catch (e: Exception){

        }
    }

    val locationListener = object: LocationListener {

        override fun onLocationChanged(p0: Location) {
            val geocoder = Geocoder(this@Geolocation, Locale.getDefault())
            try {
                val a = geocoder.getFromLocation(p0.latitude, p0.longitude, 1)
                if(a!!.isNotEmpty()){
                    val ad = a[0]
                    Log.d("location", "${ad.locality}${ad.countryName}")
                }
            } catch (e: Exception){

            }
        }

    }

    fun gpsIsEnable(){
        if(!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            && !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }
    }

}
