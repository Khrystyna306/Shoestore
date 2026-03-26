package com.example.shoestore

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MenuActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        prefs = getSharedPreferences("UserData", MODE_PRIVATE)

        val tvWelcome = findViewById<TextView>(R.id.tvWelcome)
        val btnLogout = findViewById<Button>(R.id.btnLogout)

        // Отримуємо ім'я і прізвище
        val firstName = prefs.getString("firstName", "")
        val lastName = prefs.getString("lastName", "")

        tvWelcome.text = "Привіт, $firstName $lastName!"

        // Logout
        btnLogout.setOnClickListener {
            prefs.edit().putBoolean("isAuthorized", false).apply()

            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}