package com.ssafy.cleanstore.dto

data class Stuff(
    var productName: String,
    var productCount: Int
){
    override fun toString() = "λ¬Όν: $productName -> μλ: $productCount"
}
