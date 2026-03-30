package com.example.shoestore

data class Product(
    var name: String,
    var price: Double,
    var description: String,
    var imageResId: Int = R.drawable.ic_launcher_foreground,
    var imageUri: String? = null // якщо користувач вибрав фото з галереї
)