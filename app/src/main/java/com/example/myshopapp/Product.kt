package com.example.myshopapp

data class Product(
    val id: Int,
    val name: String,
    val category: String,
    val price: Double,
    val description: String,
    val imageResName: String
)

val sampleProducts = listOf(
    Product(1, "Pro Smartphone 15", "Smartphones", 999.99, "Latest flagship smartphone with a stunning OLED display and pro-grade camera system.", "phone"),
    Product(2, "UltraBook Air 13", "Laptops", 1299.99, "Power meets portability. High-performance laptop with all-day battery life.", "macbook"),
    Product(3, "Noise Cancelling Headphones", "Audio", 299.99, "Immerse yourself in sound with industry-leading noise cancellation technology.", "headphone"),
    Product(4, "Smart Watch Series 9", "Wearables", 399.99, "Track your health and stay connected with the most advanced smartwatch yet.", "watch")
)
