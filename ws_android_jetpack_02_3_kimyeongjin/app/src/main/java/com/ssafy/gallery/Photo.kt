package com.ssafy.gallery

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Photo")
data class Photo(
    @PrimaryKey
    val id: Int,
    val location: String,
    val date: Int,
    val src: String
)


//    ➢ 테이블 이름 : photos
//    ➢ 테이블 컬럼 : NUM(INTEGER, PRIMARY KEY)
//    PHOTO_LOCATION (CHAR(200))
//    PHOTO_DATE (CHAR(200))
//    PHOTO_SRC (LONG