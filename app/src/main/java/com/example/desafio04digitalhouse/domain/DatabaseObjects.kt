package com.example.desafio04digitalhouse.domain

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Game(
    var title: String? = "",
    var launchYear: String? = "",
    var description: String? = "",
    var imagePath: String? = ""
)