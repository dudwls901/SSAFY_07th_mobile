package com.ssafy.gallery.database

import androidx.room.Dao
import androidx.room.Query
import com.ssafy.gallery.Photo

@Dao
interface GalleryDao {

    @Query("select * from Photo")
    suspend fun selectAllPhotos(): MutableList<Photo>

    @Query("select * from Photo where id = (:id)")
    suspend fun selectPhoto(id: Long): Photo

}