package com.ssafy.ssafygo.dto

import java.io.Serializable

data class StoreDTO(var name: String, var tel: String, val imgUrl: String, var lat: Double, var lng: Double)
    : Serializable {

    var id : Int = -1

    constructor(_id: Int, name: String, tel: String, imgUrl: String, lat: Double, lng: Double): this(name, tel, imgUrl, lat, lng) {
        id = _id
    }

    override fun toString(): String {
        return "가맹점이름 : $name\n 전화번호 : $tel\n 이미지 경로: $imgUrl\n 위도 : $lat\n 경도 : $lng"
    }
}