package com.example.desafio04digitalhouse

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()
        findViewById<Button>(R.id.btn_signUp).setOnClickListener {
            signUp()
        }
    }

    private fun signUp() {
        val email = findViewById<EditText>(R.id.edt_email)
        val password = findViewById<EditText>(R.id.edt_password)
        val repeatPassword = findViewById<EditText>(R.id.edt_repeat_password)

        if (password.text != repeatPassword.text) {
            Toast.makeText(applicationContext, "Passwords don't match", Toast.LENGTH_SHORT).show()
        } else {
            auth.createUserWithEmailAndPassword(email.toString(), password.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("Register", "SignUpWithEmail:Successful")
                            navigateToGame()
                        } else {
                            Log.w("Register", "SignUpWithEmail:Failure")
                            Toast.makeText(applicationContext, "Sign Up failed", Toast.LENGTH_SHORT).show()
                        }
            }
        }
    }

    private fun navigateToGame() = startActivity(Intent(applicationContext, GameActivity::class.java))
}