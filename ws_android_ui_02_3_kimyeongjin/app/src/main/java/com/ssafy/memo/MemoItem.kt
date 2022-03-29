package com.ssafy.memo

import java.net.ContentHandler

data class MemoItem(
    var title: String,
    var content: String,
    var date: String
){

    override fun toString(): String {
        return "MemoItem(title='$title', content='$content', date='$date')"
    }

}
