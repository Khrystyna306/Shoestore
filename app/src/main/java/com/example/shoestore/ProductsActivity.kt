package com.example.shoestore

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ProductsActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences
    private lateinit var productAdapter: ProductAdapter
    private var productList = mutableListOf<Product>()

    private lateinit var pickImageLauncher: ActivityResultLauncher<Intent>
    private var currentEditPosition: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)

        prefs = getSharedPreferences("ProductsData", Context.MODE_PRIVATE)

        val rvProducts = findViewById<RecyclerView>(R.id.rvProducts)
        val btnAdd = findViewById<Button>(R.id.btnAddProduct)

        productList = loadProducts()

        pickImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK && result.data != null && currentEditPosition != -1) {
                val uri: Uri? = result.data!!.data
                uri?.let {
                    productList[currentEditPosition].imageUri = it.toString()
                    productAdapter.notifyItemChanged(currentEditPosition)
                    saveProducts()
                }
            }
        }

        productAdapter = ProductAdapter(
            context = this,
            products = productList,
            onDataChanged = { saveProducts() },
            onChangePhotoRequested = { position ->
                currentEditPosition = position
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                pickImageLauncher.launch(intent)
            }
        )

        rvProducts.layoutManager = LinearLayoutManager(this)
        rvProducts.adapter = productAdapter

        btnAdd.setOnClickListener {
            val newProduct = Product(
                name = "New Product",
                price = 99.99,
                description = "Опис нового продукту",
                imageResId = R.drawable.ic_launcher_foreground
            )
            productList.add(newProduct)
            productAdapter.notifyItemInserted(productList.size - 1)
            saveProducts()
        }
    }

    private fun saveProducts() {
        val json = Gson().toJson(productList)
        prefs.edit {
            putString("products_list", json)
        }
    }

    private fun loadProducts(): MutableList<Product> {
        val json = prefs.getString("products_list", null)
        return if (!json.isNullOrEmpty()) {
            val type = object : TypeToken<MutableList<Product>>() {}.type
            Gson().fromJson<MutableList<Product>>(json, type)
        } else {
            mutableListOf(
                Product("Nike Air Max", 120.0, "Комфортні та стильні кросівки", R.drawable.ic_launcher_foreground),
                Product("Adidas Superstar", 110.0, "Класичні кеди Adidas", R.drawable.ic_launcher_foreground),
                Product("Puma RS-X", 130.0, "Сучасний дизайн та комфорт", R.drawable.ic_launcher_foreground),
                Product("New Balance 574", 100.0, "Класичні кросівки NB", R.drawable.ic_launcher_foreground),
                Product("Vans Old Skool", 90.0, "Легендарні Vans", R.drawable.ic_launcher_foreground),
                Product("Converse Chuck Taylor", 95.0, "Ікона стилю Converse", R.drawable.ic_launcher_foreground),
                Product("Reebok Classic", 105.0, "Класика від Reebok", R.drawable.ic_launcher_foreground),
                Product("Asics Gel-Lyte", 115.0, "Комфорт та підтримка стопи", R.drawable.ic_launcher_foreground)
            )
        }
    }
}