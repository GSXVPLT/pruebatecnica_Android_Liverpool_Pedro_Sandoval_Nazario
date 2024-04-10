package com.example.pruebatecnica_android_liverpool_pedro_sandoval_nazario.Clases

data class Product(
    val imageUrl: String,
    val description: String,
    val originalPrice: Double,
    val discountedPrice: Double,
    val colorVariants: List<String>
)
