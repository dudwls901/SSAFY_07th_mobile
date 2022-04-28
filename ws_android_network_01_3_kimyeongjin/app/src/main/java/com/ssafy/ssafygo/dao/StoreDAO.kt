package com.ssafy.ssafygo.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.ssafy.ssafygo.dto.StoreDTO
import java.sql.SQLException

class StoreDAO {
    //DB선언부
    lateinit var helper: DBHelper
    lateinit var sqlDB: SQLiteDatabase
    private var mCtx: Context? = null
    val DB_NAME = "clean_store"
    val ID = "_id"
    val NAME = "name"
    val TEL = "tel"
    val LAT = "lat"
    val LNG = "lng"
    val TABLE_NAME = "Store"
    val defaultStore: StoreDTO =
        StoreDTO(1, "싸피벅스", "010-1234-5678", 36.10830144233874, 128.41827450414362)

    // 가맹점 초기 등록
    private fun storeInitInsert(store: StoreDTO) {
        val values = ContentValues()
        values.put(NAME, store.name)
        values.put(TEL, store.tel)
        values.put(LAT, store.lat)
        values.put(LNG, store.lng)
        sqlDB.insert(TABLE_NAME, null, values)
    }

    //COUNT
    private fun getCount() : Int{
        sqlDB.rawQuery("SELECT COUNT($ID) FROM $TABLE_NAME", null).use {
            return if (it.moveToFirst())
                it.getInt(0)
            else
                0
        }
    }

    // 특정 가맹점 조회 method
    fun storeSelect(storeId: Int): StoreDTO {
        sqlDB.rawQuery(
            "SELECT $ID, $NAME, $TEL, $LAT, $LNG FROM $TABLE_NAME WHERE $ID = ?",
            arrayOf(storeId.toString())
        ).use {
            it.moveToFirst()
            return StoreDTO(
                it.getInt(0),
                it.getString(1),
                it.getString(2),
                it.getDouble(3),
                it.getDouble(4)
            )
        }
    }

    // 가맹점 조회 method
    fun storeSelectAll(): List<StoreDTO> {
        val list = mutableListOf<StoreDTO>()
        sqlDB.rawQuery("SELECT $ID, $NAME, $TEL, $LAT, $LNG FROM $TABLE_NAME", null).use {
            if (it.moveToFirst()) {
                do {
                    list.add(
                        StoreDTO(
                            it.getInt(0),
                            it.getString(1),
                            it.getString(2),
                            it.getDouble(3),
                            it.getDouble(4)
                        )
                    )
                } while (it.moveToNext())
            }
        }
        return list
    }

    // 가맹점정보 변경
    fun storeUpdate(store: StoreDTO): Int {
        val values = ContentValues()
        values.put(NAME, store.name)
        values.put(TEL, store.tel)
        values.put(LAT, store.lat)
        values.put(LNG, store.lng)

        return sqlDB.update(TABLE_NAME, values, "$ID = ?", arrayOf(store.id.toString()))
    }

    // 가맹점 삭제 method
    fun storeDelete(storeId: Int): Int {
        return sqlDB.delete(TABLE_NAME, "$ID = ?" , arrayOf(storeId.toString()))
    }

    // DB에 데이터가 있는지 확인
    private fun isAnyDataExists(): Boolean {
        // 가맹점 정보가 1개 이상 있다면 true 반환
        return getCount() >= 1
    }

    @Throws(SQLException::class)
    fun open() {
        helper = DBHelper(mCtx!!)
        sqlDB = helper.writableDatabase

        // 가맹점이 없다면 기본 가맹점 등록
        if (!isAnyDataExists()) {
            storeInitInsert(defaultStore)
        }
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
        sqlDB.close()
    }

    inner class DBHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, 1) {
        // 테이블 생성
        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL(
                """CREATE TABLE if not exists $TABLE_NAME (
                    $ID INTEGER PRIMARY KEY AUTOINCREMENT,
                    $NAME VARCHAR(50),
                    $TEL VARCHAR(20),
                    $LAT DOUBLE,
                    $LNG DOUBLE
                )
                """
            )
        }

        override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) { //테이블 삭제 후 생성
            db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
            onCreate(db)
        }
    }
}