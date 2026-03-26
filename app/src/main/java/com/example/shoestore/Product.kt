package com.example.shoestore

import java.io.Serializable

data class Product(
    var name: String,
    var price: Double,
    var description: String,
    var imageResId: Int
) : Serializable