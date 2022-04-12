package com.ssafy.cleanstore.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.ssafy.cleanstore.dto.Stuff
import java.sql.SQLException

private const val TAG = "StuffDao"
class StuffDao {
    //DB선언부
    lateinit var helper: DBHelper
    lateinit var sqlDB: SQLiteDatabase

    private var mCtx: Context? = null
    private val DB_NAME = "clean_store"
    private val TABLE_NAME = "Stuff"
    private val STUFF_ID = "_id"
    private val STUFF_NAME = "name"
    private val STUFF_CNT = "count"

    @Throws(SQLException::class)
    fun open() {
        helper = DBHelper(mCtx!!)
        Log.d(TAG, "open: stuff")
        sqlDB = helper.writableDatabase
    }

    fun dbOpenHelper(context: Context) {
        this.mCtx = context
    }

    fun create() {
        // DB생성
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
    // 물품 CRUD 구현

    // 물품 등록
    fun stuffInsert(stuff: Stuff): Long {
        Log.d(TAG, "stuffInsert: $stuff")
        val contentValues = ContentValues()
        contentValues.put(STUFF_NAME, stuff.name)
        contentValues.put(STUFF_CNT, stuff.count)
        sqlDB.beginTransaction()
        val result = sqlDB.insert(TABLE_NAME, null, contentValues)
        if(result >0){
            sqlDB.setTransactionSuccessful()
        }
        sqlDB.endTransaction()
        Log.d(TAG, "stuffInsert: after ${stuffSelectAll().size}")
        return result
    }

    // 특정 물품 조회 method
    fun stuffSelect(stuffId: Int): Stuff? {
        val query = "SELECT * FROM $TABLE_NAME where _id=$stuffId"
        val cursor = sqlDB.rawQuery(query, null)
        var result = Stuff("",0)
        while(cursor.moveToNext()){
            result = Stuff(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getInt(2)
            )
        }
        return result
    }

    // 물품 조회 method
    fun stuffSelectAll(): MutableList<Stuff> {
        val list = mutableListOf<Stuff>()
        val cursor = sqlDB.rawQuery("select * from $TABLE_NAME", null)
        while(cursor.moveToNext()){
            list.add(
                Stuff(
                    id = cursor.getInt(0),
                    name = cursor.getString(1),
                    count = cursor.getInt(2)
                )
            )
        }
        return list
    }

    // 물품정보 변경
    fun stuffUpdate(stuff: Stuff): Int {
        val contentValues = ContentValues()
        contentValues.put(STUFF_ID,stuff.id)
        contentValues.put(STUFF_NAME,stuff.name)
        contentValues.put(STUFF_CNT,stuff.count)
        sqlDB.beginTransaction()
        val result = sqlDB.update(TABLE_NAME, contentValues, "_id=?", arrayOf(stuff.id.toString()))
        if(result>0){
            sqlDB.setTransactionSuccessful()
        }
        sqlDB.endTransaction()
        return result
    }

    // 물품 삭제 method
    fun stuffDelete(stuffId: Int): Int {
        sqlDB.beginTransaction()
        val result = sqlDB.delete(TABLE_NAME, "_id=?", arrayOf(stuffId.toString()))
        if(result>0){
            sqlDB.setTransactionSuccessful()
        }
        sqlDB.endTransaction()
        return result
    }

    inner class DBHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, 1) {
        // 테이블 생성
        override fun onCreate(db: SQLiteDatabase) {
            val query: String = "CREATE TABLE IF NOT EXISTS $TABLE_NAME ( $STUFF_ID integer primary key autoincrement, $STUFF_NAME text, $STUFF_CNT text);";
            db.execSQL(query)
        }

        // 테이블 삭제 후 생성
        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) { //테이블 삭제 후 생성
            val sql = "DROP TABLE IF EXISTS $TABLE_NAME"
            db.execSQL(sql)
            onCreate(db)
        }
    }
}