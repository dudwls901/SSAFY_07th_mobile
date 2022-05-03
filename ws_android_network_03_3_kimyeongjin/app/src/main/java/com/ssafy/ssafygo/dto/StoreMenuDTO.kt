package com.ssafy.ssafygo.dto

import java.io.Serializable

data class StoreMenuDTO(var name: String, var price: Int, var storeId: Int)
    : Serializable {

    var id : Int = -1

    constructor(_id: Int, name: String, price: Int, storeId: Int): this(name, price, storeId) {
        id = _id
    }

    override fun toString(): String {
        return "이름 : $name\n 가격 : ${price}원"
    }
}