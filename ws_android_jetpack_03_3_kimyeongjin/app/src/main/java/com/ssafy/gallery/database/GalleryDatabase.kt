package com.ssafy.gallery.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ssafy.gallery.Photo


@Database(entities = [Photo::class], version = 2)
abstract class GalleryDatabase: RoomDatabase() {
    abstract fun galleryDao(): GalleryDao
}