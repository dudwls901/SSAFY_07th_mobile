package com.ssafy.gallery

data class Photo(
    val num: Int,
    val location: String,
    val date: Int,
    val src: String
)


//    ➢ 테이블 이름 : photos
//    ➢ 테이블 컬럼 : NUM(INTEGER, PRIMARY KEY)
//    PHOTO_LOCATION (CHAR(200))
//    PHOTO_DATE (CHAR(200))
//    PHOTO_SRC (LONG