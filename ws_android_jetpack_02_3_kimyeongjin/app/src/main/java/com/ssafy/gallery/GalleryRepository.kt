package com.ssafy.gallery

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.room.withTransaction
import com.ssafy.gallery.database.GalleryDatabase
import java.lang.IllegalStateException

private const val TAG = "GalleryRepository_sss"
private const val DATABASE_NAME = "gallery-database"
class GalleryRepository private constructor(context: Context){
    private val database: GalleryDatabase = Room.databaseBuilder(
        context.applicationContext,
        GalleryDatabase::class.java,
        DATABASE_NAME
    ).fallbackToDestructiveMigration()
        .build()

    private val galleryDao = database.galleryDao()

    suspend fun selectAllPhotos(): MutableList<Photo> = database.withTransaction {
        val a = galleryDao.selectAllPhotos()
        Log.d(TAG, "selectAllPhotos: ${a.size}")
        a
    }

    suspend fun selectPhoto(id: Long): Photo = database.withTransaction {
        galleryDao.selectPhoto(id)
    }

 companion object{
     private var INSTANCE: GalleryRepository? = null

     fun getInstance(context: Context): GalleryRepository{
         if(INSTANCE==null){
             INSTANCE = GalleryRepository(context)
         }
         return INSTANCE?: throw IllegalStateException("초기화해주세용.")
     }
 }

}