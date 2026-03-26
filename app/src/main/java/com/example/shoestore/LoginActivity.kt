package com.example.shoestore

import android.content.Intent
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
        val isAuthorized = prefs.getBoolean("isAuthorized", false)

        if (isAuthorized) {
            startActivity(Intent(this, MenuActivity::class.java))
            finish()
        }
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


                prefs.edit().putBoolean("isAuthorized", true).apply()

                startActivity(Intent(this, MenuActivity::class.java))
                finish()

            } else {
                Toast.makeText(this, "Невірний логін або пароль", Toast.LENGTH_SHORT).show()
            }
        }
    }
}