package com.example.desafio04digitalhouse.domain

import com.google.firebase.database.IgnoreExtraProperties
import java.io.Serializable

@IgnoreExtraProperties
data class Game(
    var title: String? = "",
    var launchYear: String? = "",
    var description: String? = "",
    var imagePath: String? = ""
) : Serializable