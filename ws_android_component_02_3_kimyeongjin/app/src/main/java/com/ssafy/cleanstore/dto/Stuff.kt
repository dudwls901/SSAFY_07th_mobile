package com.ssafy.cleanstore.dto

data class Stuff(
    var productName: String,
    var productCount: Int
){
    override fun toString() = "물품: $productName -> 수량: $productCount"
}
