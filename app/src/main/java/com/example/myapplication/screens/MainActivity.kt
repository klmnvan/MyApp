package com.example.myapplication.screens

import android.content.ContentValues.TAG
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.api.AdapterCatalog
import com.example.myapplication.api.ApiRequest
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.models.Catalog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val adapterCatalog = AdapterCatalog()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getCatalog()
        binding.tEmail.addTextChangedListener {
            Log.d("email", binding.tEmail.text.toString().isEmailValid().toString())
            Toast.makeText(this@MainActivity, binding.tEmail.text.toString().isEmailValid().toString(), Toast.LENGTH_SHORT).show()
        }
    }

    /** Функция для проверки email на соответствие паттерну name@domenname.ru */
    private fun String.isEmailValid(): Boolean{
        return !TextUtils.isEmpty(this) &&android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }

    private fun getCatalog() {

        //Описание запроса к api
        val api = Retrofit.Builder()
            .baseUrl("https://iis.ngknn.ru/NGKNN/МамшеваЮС/MedicMadlab/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequest::class.java)

        //Сам запрос делаем в отдельном потоке
        CoroutineScope(Dispatchers.IO).launch {
            try {
                //Делаем запрос и получаем ответ в переменную
                val response = api.getCatalog().awaitResponse()
                //Проверяем, успешный ли запрос
                if (response.isSuccessful) {
                    //Если да, парсим
                    val data = response.body()!!
                    //Когда получили ответ, запускаем функцию парса данных в recyclerview
                    runOnUiThread { parseCatalog(data) }
                    Log.d(TAG, data.toString())
                }
            } catch (e: Exception) {
                Log.d(TAG, e.message.toString())
            }
        }

    }

    private fun parseCatalog(listCatalog: List<Catalog>) {
        with(binding) {
            listCatalogView.layoutManager = GridLayoutManager(this@MainActivity, 1)
            listCatalogView.adapter = adapterCatalog
            listCatalog.forEach {
                adapterCatalog.addCatalog(it)
            }
        }
    }
}