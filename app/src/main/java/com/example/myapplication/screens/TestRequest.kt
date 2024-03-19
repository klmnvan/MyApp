package com.example.myapplication.screens

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityTestRequestBinding
import com.example.myapplication.objects.Requests
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TestRequest : AppCompatActivity() {
    lateinit var binding: ActivityTestRequestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        CoroutineScope(Dispatchers.Main).launch {
            try {
                //Requests.signIn("nesklmnvan@gmail.com", "pass12345")
                val test = Requests.getTest()
                val p = Requests.getProfile()

                Log.d("get", test.toString() + p.toString())
            } catch (e: Exception) {
                Log.d("getError", e.message.toString())
            }
        }
    }
}