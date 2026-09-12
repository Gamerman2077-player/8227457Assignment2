package com.example.s8227457assignment2.data.model

data class Investment(
    val assetType: String,
    val ticker: String,
    val currentPrice: Double,
    val dividendYield: Double,
    val description: String
)