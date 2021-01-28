package com.example.desafio04digitalhouse

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class GameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        findViewById<Button>(R.id.btn_signOut).setOnClickListener {
            Firebase.auth.signOut()
            startActivity(Intent(applicationContext, LoginActivity::class.java))
        }
    }
}