package com.example.myapplication.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.text.DecimalFormat

@Serializable
data class Test(
    var id: String = "",
    @SerialName("created_at")
    var createdAt: String = "",
    var name: String = "",
    var money: Double = 0.0
)
