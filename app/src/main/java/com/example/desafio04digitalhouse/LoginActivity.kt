package com.example.desafio04digitalhouse

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        labelsEditText()

        NavigateToRegister()
    }

    private fun NavigateToRegister() {
        val registerButton = findViewById<Button>(R.id.btn_create_account)
        registerButton.setOnClickListener {
            startActivity(Intent(applicationContext, RegisterActivity::class.java))
        }
    }

    private fun labelsEditText() {
        val emailEditText = findViewById<EditText>(R.id.edt_email)
        val passwordEditText = findViewById<EditText>(R.id.edt_password)
        val emailTextView = findViewById<TextView>(R.id.tv_email)
        val passwordTextView = findViewById<TextView>(R.id.tv_password)

        emailEditText.afterTextChanged { text ->
            if (text == "") {
                emailTextView.visibility = View.INVISIBLE
            } else emailTextView.visibility = View.VISIBLE
        }

        passwordEditText.afterTextChanged { text ->
            if (text == "") passwordTextView.visibility = View.GONE
            else passwordTextView.visibility = View.VISIBLE
        }
    }

    fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }
        })
    }
}