package com.example.desafio04digitalhouse

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        labelsEditText()
    }

    private fun labelsEditText() {
        val nameTextView = findViewById<TextView>(R.id.tv_name)
        val nameEditText = findViewById<EditText>(R.id.edt_name)
        val emailEditText = findViewById<EditText>(R.id.edt_email)
        val passwordEditText = findViewById<EditText>(R.id.edt_password)
        val emailTextView = findViewById<TextView>(R.id.tv_email)
        val passwordTextView = findViewById<TextView>(R.id.tv_password)

        nameEditText.afterTextChanged { text ->
            if (text == "")
                nameTextView.visibility = View.INVISIBLE
            else nameTextView.visibility = View.VISIBLE
        }

        emailEditText.afterTextChanged { text ->
            if (text == "") {
                emailTextView.visibility = View.GONE
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