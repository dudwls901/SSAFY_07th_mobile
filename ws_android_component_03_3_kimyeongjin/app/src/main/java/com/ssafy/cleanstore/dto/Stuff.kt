package com.ssafy.cleanstore.dto

import android.content.Intent
import java.io.Serializable

// Intent.getXXXExtra() -> Intent.getSerializableExtra("stuff") as Stuff
// Intent.putExtra("stuff", Stuff객체)
data class Stuff (var name: String?, var count: Int) : Serializable {
    var id = -1

    constructor(id: Int, name: String, count: Int) : this(name, count) {
        this.id = id
    }

    override fun toString(): String {
        return "물품 : $name -> 수량 : $count"
    }
}