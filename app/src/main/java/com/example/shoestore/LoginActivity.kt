package com.example.shoestore

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        prefs = getSharedPreferences("UserData", MODE_PRIVATE)

        val etLogin = findViewById<EditText>(R.id.etLogin)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener {

            val login = etLogin.text.toString()
            val password = etPassword.text.toString()

            val savedLogin = prefs.getString("login", "")
            val savedPassword = prefs.getString("password", "")

            if (login == savedLogin && password == savedPassword) {
                Toast.makeText(this, "Вхід успішний", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Невірний логін або пароль", Toast.LENGTH_SHORT).show()
            }
        }
    }
}