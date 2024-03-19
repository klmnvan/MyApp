package com.example.myapplication.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.text.DecimalFormat

@Serializable
data class Profile(
    var id: String = "",
    @SerialName("updated_at")
    var updAt: String? = "",
    var name: String? = "",
    var phone: String? = "",
    @SerialName("profile_image")
    var img: String? = "",
    var email: String? = "",
    @SerialName("current_balance")
    var balance: Double? = 0.0
)
