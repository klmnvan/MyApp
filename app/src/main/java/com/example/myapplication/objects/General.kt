package com.example.myapplication.objects

import android.text.TextUtils

object General {

    fun String.emailIsValid(): Boolean {
        return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }

}