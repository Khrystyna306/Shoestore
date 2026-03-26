package com.example.shoestore

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ProductsActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences
    private lateinit var productAdapter: ProductAdapter
    private val productList = mutableListOf<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)

        prefs = getSharedPreferences("ProductsData", MODE_PRIVATE)

        val rvProducts = findViewById<RecyclerView>(R.id.rvProducts)
        val btnAdd = findViewById<Button>(R.id.btnAddProduct)

        rvProducts.layoutManager = LinearLayoutManager(this)
        productAdapter = ProductAdapter(this, productList)
        rvProducts.adapter = productAdapter

        // Приклад початкових товарів
        loadInitialProducts()

        btnAdd.setOnClickListener {
            // Додаємо новий товар (для прикладу фіксовані дані)
            val newProduct = Product(
                "New Product",
                99.99,
                "Опис нового продукту",
                R.drawable.ic_launcher_foreground
            )
            productList.add(newProduct)
            productAdapter.notifyItemInserted(productList.size - 1)
        }
    }

    private fun loadInitialProducts() {
        val initialProducts = listOf(
            Product("Nike Air Max", 120.0, "Комфортні та стильні кросівки", R.drawable.ic_launcher_foreground),
            Product("Adidas Superstar", 110.0, "Класичні кеди Adidas", R.drawable.ic_launcher_foreground),
            Product("Puma RS-X", 130.0, "Сучасний дизайн та комфорт", R.drawable.ic_launcher_foreground),
            Product("New Balance 574", 100.0, "Класичні кросівки NB", R.drawable.ic_launcher_foreground),
            Product("Vans Old Skool", 90.0, "Легендарні Vans", R.drawable.ic_launcher_foreground),
            Product("Converse Chuck Taylor", 95.0, "Ікона стилю Converse", R.drawable.ic_launcher_foreground),
            Product("Reebok Classic", 105.0, "Класика від Reebok", R.drawable.ic_launcher_foreground),
            Product("Asics Gel-Lyte", 115.0, "Комфорт та підтримка стопи", R.drawable.ic_launcher_foreground)
        )
        productList.addAll(initialProducts)
        productAdapter.notifyDataSetChanged()
    }
}