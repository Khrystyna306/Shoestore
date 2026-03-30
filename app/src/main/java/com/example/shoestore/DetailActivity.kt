package com.example.shoestore

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val product = intent.getSerializableExtra("product") as? Product ?: return

        val ivProduct = findViewById<ImageView>(R.id.ivProductDetail)
        val tvName = findViewById<TextView>(R.id.tvNameDetail)
        val tvPrice = findViewById<TextView>(R.id.tvPriceDetail)
        val tvDescription = findViewById<TextView>(R.id.tvDescriptionDetail)

        ivProduct.setImageResource(product.imageResId)
        tvName.text = product.name
        tvPrice.text = "$${product.price}"
        tvDescription.text = product.description
    }
}