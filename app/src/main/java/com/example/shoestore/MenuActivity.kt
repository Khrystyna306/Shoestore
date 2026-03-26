package com.example.shoestore

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MenuActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        prefs = getSharedPreferences("UserData", MODE_PRIVATE)

        val tvWelcome = findViewById<TextView>(R.id.tvWelcome)
        val btnProducts = findViewById<Button>(R.id.btnProducts)
        val btnCategories = findViewById<Button>(R.id.btnCategories)
        val btnProfile = findViewById<Button>(R.id.btnProfile)
        val btnLogout = findViewById<Button>(R.id.btnLogout)

        // Встановлюємо привітання з ім'ям
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