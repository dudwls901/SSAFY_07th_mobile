package com.ssafy.memo.util

import java.text.SimpleDateFormat
import java.util.*

class Utils {

    companion object{
        fun formatter(): SimpleDateFormat {
            val formatter = SimpleDateFormat("MM-dd hh:mm")
            formatter.timeZone = TimeZone.getTimeZone("GMT+9")

            return formatter
        }
    }
}