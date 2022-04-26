package com.ssafy.gallery

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.google.android.material.tabs.TabLayout
import java.sql.SQLException

const val TAG = "GalleryDao_sss"
class GalleryDao {
    // DB선언부
    lateinit var helper: DBHelper
    lateinit var sqlDB: SQLiteDatabase
    private var mCtx: Context? = null

    private val DB_NAME = "photos"
    private val TABLE_NAME = "photos"
    private val ID = "NUM"
    private val PHOTO_LOCATION = "photoLocation"
    private val PHOTO_DATE = "photoDate"
    private val PHOTO_SRC = "photoSrc"

    @Throws(SQLException::class)
    fun open() {
        helper = DBHelper(mCtx!!)
        sqlDB = helper.writableDatabase
    }
    fun dbOpenHelper(context: Context) {
        this.mCtx = context
    }
    fun create() {
        //DB생성
        helper.onCreate(sqlDB)
    }
    fun upgrade(oldVersion: Int, newVersion: Int) {
        //DB version 변경
        helper.onUpgrade(sqlDB, 1, 2)
    }
    fun close() {
        //DB종료
        if (sqlDB != null && sqlDB.isOpen())
        {
            sqlDB.close()
        }

    }


    // 특정 물품 조회 method
    fun selectPhoto(photoNum: Int): Photo? {
        sqlDB.rawQuery("SELECT * FROM $TABLE_NAME WHERE NUM = $photoNum",null).use{
            return if(it.moveToFirst())
                Photo(it.getInt(0), it.getString(1), it.getInt(2), it.getString(3))
            else
                null
        }
    }

    // 물품 조회 method
    fun selectAllPhotos(): MutableList<Photo> {
        val list: ArrayList<Photo> = arrayListOf()
        sqlDB.rawQuery("SELECT $ID, $PHOTO_LOCATION, $PHOTO_DATE, $PHOTO_SRC FROM $TABLE_NAME",null).use{
            if(it.moveToFirst()){
                do{
                    list.add(Photo(it.getInt(0), it.getString(1), it.getInt(2), it.getString(3)))
                }while(it.moveToNext())
            }
        }
        Log.d(TAG, "selectAllPhotos: ${list[0]}")
        return list
    }


    inner class DBHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, 1) {

        // 테이블 생성
        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL("""CREATE TABLE if not exists $TABLE_NAME (
                    $ID INTEGER PRIMARY KEY AUTOINCREMENT,
                    $PHOTO_LOCATION TEXT,
                    $PHOTO_DATE INTEGER,
                    $PHOTO_SRC TEXT
                )
                """)
        }

        override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) { //테이블 삭제 후 생성
            db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
            onCreate(db)
        }
    }
}