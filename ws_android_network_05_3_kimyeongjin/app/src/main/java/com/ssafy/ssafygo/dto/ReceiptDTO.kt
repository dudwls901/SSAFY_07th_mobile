package com.ssafy.ssafygo.dto

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class ReceiptDTO(var menuName: String, var orderDate: String, var price: Long)
    : Parcelable {

    var id : Int = -1

    constructor(parcel: Parcel) : this(
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readLong()
    ) {
        id = parcel.readInt()
    }

    constructor(_id: Int, _name: String, _date: String, _price: Long): this(_name, _date, _price) {
        id = _id
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(menuName)
        parcel.writeString(orderDate)
        parcel.writeLong(price)
        parcel.writeInt(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return " 이름 : $menuName\n 주문일자 : ${orderDate}\n 가격: ${price}원"
    }

    companion object CREATOR : Parcelable.Creator<ReceiptDTO> {
        override fun createFromParcel(parcel: Parcel): ReceiptDTO {
            return ReceiptDTO(parcel)
        }

        override fun newArray(size: Int): Array<ReceiptDTO?> {
            return arrayOfNulls(size)
        }
    }

}