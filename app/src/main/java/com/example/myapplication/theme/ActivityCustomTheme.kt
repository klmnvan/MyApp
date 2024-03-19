package com.example.myapplication.theme

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.R
import com.example.myapplication.objects.PrefManager
import com.example.myapplication.objects.UserData

class ActivityCustomTheme : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PrefManager.initPref(this@ActivityCustomTheme)
        if(!PrefManager.isLightTheme){
            UserData.theme = R.style.DarkMode
        }
        setTheme(UserData.theme)
    }
}