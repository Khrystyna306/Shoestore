package com.example.shoestore

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ProductsActivity : AppCompatActivity() {

    private lateinit var sharedPrefs: android.content.SharedPreferences
    private lateinit var productAdapter: ProductAdapter
    private var productList = mutableListOf<Product>()

    private lateinit var pickImageLauncher: ActivityResultLauncher<Intent>
    private var currentEditPosition: Int = -1
    private var isAddingNewProduct: Boolean = false
    private var tempSelectedImageUri: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)

        sharedPrefs = getSharedPreferences("ProductsData", Context.MODE_PRIVATE)
        val rvProducts = findViewById<RecyclerView>(R.id.rvProducts)
        val btnAdd = findViewById<Button>(R.id.btnAddProduct)

        // Завантаження товарів
        productList = loadProducts()

        // Лончер для вибору фото
        pickImageLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK && result.data != null) {
                    val uri: Uri? = result.data!!.data
                    uri?.let {
                        if (isAddingNewProduct) {
                            tempSelectedImageUri = it.toString()
                        } else if (currentEditPosition != -1) {
                            productList[currentEditPosition].imageUri = it.toString()
                            productAdapter.notifyItemChanged(currentEditPosition)
                            saveProducts()
                        }
                    }
                }
            }

        // Адаптер
        productAdapter = ProductAdapter(
            context = this,
            products = productList,
            onDataChanged = { saveProducts() },
            onChangePhotoRequested = { position ->
                currentEditPosition = position
                isAddingNewProduct = false
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                pickImageLauncher.launch(intent)
            }
        )

        rvProducts.layoutManager = LinearLayoutManager(this)
        rvProducts.adapter = productAdapter

        btnAdd.setOnClickListener { showAddProductDialog() }
    }

    private fun showAddProductDialog() {
        isAddingNewProduct = true
        tempSelectedImageUri = null

        val dialogView = layoutInflater.inflate(R.layout.dialog_edit_product, null)
        val etName = dialogView.findViewById<EditText>(R.id.etName)
        val etPrice = dialogView.findViewById<EditText>(R.id.etPrice)
        val etDescription = dialogView.findViewById<EditText>(R.id.etDescription)
        val btnChangePhoto = dialogView.findViewById<Button>(R.id.btnChangePhoto)

        btnChangePhoto.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            pickImageLauncher.launch(intent)
        }

        AlertDialog.Builder(this)
            .setTitle("Додати новий товар")
            .setView(dialogView)
            .setPositiveButton("Додати") { _, _ ->
                val name = etName.text.toString().ifEmpty { "Новий товар" }
                val price = etPrice.text.toString().toDoubleOrNull() ?: 0.0
                val description = etDescription.text.toString()
                val newProduct = Product(
                    name = name,
                    price = price,
                    description = description,
                    imageUri = tempSelectedImageUri
                )
                productList.add(newProduct)
                productAdapter.notifyItemInserted(productList.size - 1)
                saveProducts()
            }
            .setNegativeButton("Відміна", null)
            .show()
    }

    private fun saveProducts() {
        val json = Gson().toJson(productList)
        sharedPrefs.edit().putString("products_list", json).apply()
    }

    private fun loadProducts(): MutableList<Product> {
        val json = sharedPrefs.getString("products_list", null)
        return if (!json.isNullOrEmpty()) {
            val type = object : TypeToken<MutableList<Product>>() {}.type
            Gson().fromJson(json, type)
        } else {
            mutableListOf(
                Product("Nike Air Max", 120.0, "Комфортні та стильні кросівки"),
                Product("Adidas Superstar", 110.0, "Класичні кеди Adidas"),
                Product("Puma RS-X", 130.0, "Сучасний дизайн та комфорт")
            )
        }
    }
}