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
/*
INSERT INTO "Photo" ("id","location","date","src") VALUES (1,'창원시-행암동',1624262862533,'@drawable/apple'),
 (2,'창원시-행암동',1624262862543,'@drawable/beach'),
 (3,'창원시-행암동',1624262862544,'@drawable/bigben'),
 (4,'창원시-행암동',1624262862545,'@drawable/bird'),
 (5,'창원시-행암동',1624262862547,'@drawable/blueberries'),
 (6,'창원시-행암동',1624262862548,'@drawable/city'),
 (7,'창원시-행암동',1624262862549,'@drawable/cornflower'),
 (8,'창원시-행암동',1624262862549,'@drawable/cute'),
 (9,'창원시-행암동',1624262862551,'@drawable/dubai'),
 (10,'창원시-행암동',1624262862552,'@drawable/duckling'),
 (11,'창원시-행암동',1624262862552,'@drawable/fantasy'),
 (12,'창원시-행암동',1624262862553,'@drawable/jellyfish'),
 (13,'창원시-행암동',1624262862554,'@drawable/milkyway'),
 (14,'창원시-행암동',1624262862557,'@drawable/mountain'),
 (15,'창원시-행암동',1624262862558,'@drawable/mountains'),
 (16,'창원시-행암동',1624262862560,'@drawable/nature'),
 (17,'창원시-행암동',1624262862561,'@drawable/nuthatch'),
 (18,'창원시-행암동',1624262862562,'@drawable/papenburg'),
 (19,'창원시-행암동',1624262862564,'@drawable/rhododendron'),
 (20,'창원시-행암동',1624262862565,'@drawable/sea'),
 (21,'창원시-행암동',1624262862566,'@drawable/sky'),
 (22,'창원시-행암동',1624262862567,'@drawable/thunderstorm'),
 (23,'창원시-행암동',1624262862568,'@drawable/tree'),
 (24,'창원시-행암동',1624262862574,'@drawable/woman');
 */