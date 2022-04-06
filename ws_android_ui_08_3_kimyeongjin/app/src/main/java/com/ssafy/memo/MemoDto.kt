package com.ssafy.memo

import com.ssafy.memo.util.Utils
import java.io.Serializable

data class MemoDto(var title : String, var content : String, var date : Long) : Serializable {

    var num : Int = -1

    constructor(_num: Int, title: String, content: String, date: Long): this(title, content, date) {
        num = _num
    }

    // listView에 표시하고 싶은 String
    override fun toString(): String {
        val sdf = Utils.formatter()
        val date = sdf.format(date)
        return "$title $date"
    }

}
