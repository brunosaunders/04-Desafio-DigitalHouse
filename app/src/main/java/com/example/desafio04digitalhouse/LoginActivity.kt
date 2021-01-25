package com.example.desafio04digitalhouse

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        navigateToRegister()

        auth = FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun navigateToRegister() {
        val registerButton = findViewById<Button>(R.id.btn_create_account)
        registerButton.setOnClickListener {
            startActivity(Intent(applicationContext, RegisterActivity::class.java))
        }
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null)
            startActivity(Intent(applicationContext, GameActivity::class.java))
    }

    fun extractFields(): List<String> {
        val email = findViewById<EditText>(R.id.edt_email)
        val password = findViewById<EditText>(R.id.edt_password)

        return listOf(email.text.toString(), password.text.toString())
    }

    private fun signInWithEmailAndPassword() {
        val fields = extractFields()
        auth.signInWithEmailAndPassword(fields[0], fields[1])
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("Login", "SignInWithEmail:Successful")
                        val user = auth.currentUser
                        updateUI(user)
                    } else {
                        Log.w("Login", "SignInWithEmail:Failure")
                        Toast.makeText(applicationContext, "Authentication failed", Toast.LENGTH_SHORT).show()
                        updateUI(null)
                    }
                }
    }
}