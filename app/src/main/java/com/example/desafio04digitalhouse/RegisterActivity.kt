package com.example.desafio04digitalhouse

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.RuntimeExecutionException
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()
        findViewById<Button>(R.id.btn_signUp).setOnClickListener {
            val email = findViewById<EditText>(R.id.edt_email)
            val password = findViewById<EditText>(R.id.edt_password)

            signUp(email.text.toString(), password.text.toString())
        }
    }

    private fun signUp(email: String, password: String) {
        if (!validateForm()) {
            return
        }

        try {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("Register", "SignUpWithEmail:Successful")
                        navigateToGame()
                    } else {
                        Log.w("Register", "SignUpWithEmail:Failure")
                        Toast.makeText(applicationContext, task.result.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }
                }

        } catch (e: RuntimeExecutionException) {
            Log.e("AuthRegister", e.toString())
            Toast.makeText(applicationContext, e.toString(), Toast.LENGTH_SHORT).show()
        }

    }


    private fun validateForm(): Boolean {
        var valid = true

        val email = findViewById<EditText>(R.id.edt_email)
        val password = findViewById<EditText>(R.id.edt_password)
        val repeatPassword = findViewById<EditText>(R.id.edt_repeat_password)

        val domain = email.text.toString().split("@").lastOrNull()

        if (domain == null || !isDomainReal(domain)) {
            email.error = "Invalid email"
            valid = false
        }

        if (password.text.toString().length < 6) {
            password.error = "Password is too short"
            valid = false
        }

        if (password.text.toString() != repeatPassword.text.toString()) {
            repeatPassword.error = "Passwords don't match"
            valid = false
        }

        return valid
    }

    private fun isDomainReal(domain: String): Boolean{
        return when (domain) {
            "gmail.com" -> true
            "yahoo.com" -> true
            "outlook.com" -> true
            "hotmail.com" -> true
            "digitalhouse.com" -> true
            else -> false
        }
    }

    private fun navigateToGame() =
        startActivity(Intent(applicationContext, GameActivity::class.java))
}
