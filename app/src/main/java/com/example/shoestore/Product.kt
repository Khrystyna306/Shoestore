package com.example.shoestore

data class Product(
    var name: String,
    var price: Double,
    var description: String,
    var imageResId: Int = R.drawable.ic_launcher_foreground, // завжди Int
    var imageUri: String? = null // фото з галереї
)