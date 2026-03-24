package com.example.shoestore

import android.app.DatePickerDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Patterns
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        prefs = getSharedPreferences("UserData", MODE_PRIVATE)

        val etFirstName = findViewById<EditText>(R.id.etFirstName)
        val etLastName = findViewById<EditText>(R.id.etLastName)
        val etBirthDate = findViewById<EditText>(R.id.etBirthDate)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etLogin = findViewById<EditText>(R.id.etLogin)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val etConfirmPassword = findViewById<EditText>(R.id.etConfirmPassword)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        // DatePicker
        etBirthDate.setOnClickListener {
            val calendar = Calendar.getInstance()

            DatePickerDialog(
                this,
                { _, year, month, day ->
                    etBirthDate.setText("$day/${month + 1}/$year")
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        btnRegister.setOnClickListener {

            val firstName = etFirstName.text.toString()
            val lastName = etLastName.text.toString()
            val birthDate = etBirthDate.text.toString()
            val email = etEmail.text.toString()
            val login = etLogin.text.toString()
            val password = etPassword.text.toString()
            val confirmPassword = etConfirmPassword.text.toString()

            if (firstName.isEmpty() || lastName.isEmpty() || birthDate.isEmpty()
                || email.isEmpty() || login.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()
            ) {
                Toast.makeText(this, "Заповніть всі поля", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Невірний email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.length < 6) {
                Toast.makeText(this, "Пароль мінімум 6 символів", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Паролі не співпадають", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Збереження
            prefs.edit().apply {
                putString("login", login)
                putString("password", password)
                apply()
            }

            Toast.makeText(this, "Реєстрація успішна", Toast.LENGTH_SHORT).show()

            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}