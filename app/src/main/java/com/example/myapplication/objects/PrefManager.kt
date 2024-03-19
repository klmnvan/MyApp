package com.example.myapplication.objects

import android.content.Context
import android.content.SharedPreferences

object PrefManager {
    lateinit var spAct: SharedPreferences
    lateinit var spUser: SharedPreferences
    lateinit var spOrder: SharedPreferences

    fun initPref(context: Context){
        spAct = context.getSharedPreferences("Act", Context.MODE_PRIVATE)
        spUser = context.getSharedPreferences("Act", Context.MODE_PRIVATE)
        spOrder = context.getSharedPreferences("Order", Context.MODE_PRIVATE)
    }

    var act: Int
        get() = spAct.getInt("act", 0)
        set(value) = spAct.edit().putInt("act", value).apply()

    var isLightTheme: Boolean
        get() = spAct.getBoolean("theme", true)
        set(value) = spAct.edit().putBoolean("theme", value).apply()

    var queueIsCreate: Boolean
        get() = spAct.getBoolean("queueCr", false)
        set(value) = spAct.edit().putBoolean("queueCr", value).apply()

    var queue: String
        get() = spAct.getString("queue", "")!!
        set(value) = spAct.edit().putString("queue", value).apply()

}