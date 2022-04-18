package com.ssafy.cleanstore.dto

import java.io.Serializable

data class Stuff (var name: String?, var count: Int, var regDate: String) : Serializable {
    var id = -1

    constructor(id: Int, name: String, count: Int, regDate: String) : this(name, count,regDate) {
        this.id = id
    }

    override fun toString(): String {
        return "물품 : $name -> 수량 : ${count}개, 입고일 : $regDate"
    }
}