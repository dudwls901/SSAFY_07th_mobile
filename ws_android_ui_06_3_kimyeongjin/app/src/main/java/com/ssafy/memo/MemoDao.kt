package com.ssafy.memo

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.sql.SQLException

class MemoDao {

    // DB선언부
    private lateinit var helper: DBHelper
    private lateinit var sqlDB: SQLiteDatabase
    private var mCtx: Context? = null


    private val DATABASE_NAME = "memo.db"
    private val DATABASE_TABLE = "memos"
    private val NUM = "num"
    private val MEMO_TITLE = "memoTitle"
    private val MEMO_CONTENT = "memoContent"
    private val MEMO_DATE = "memoDate"

    // INSERT
    fun insertMemo(dto: MemoDto): Long {
        // ContentValues를 이용한 저장
        val contentValues = ContentValues()
        contentValues.put(MEMO_TITLE, dto.title)
        contentValues.put(MEMO_CONTENT, dto.content)
        contentValues.put(MEMO_DATE, dto.date)
        Log.d("Aaaaaaa", "insertMemo: ${contentValues}")
        sqlDB.beginTransaction()
        val result = sqlDB.insert(DATABASE_TABLE, null, contentValues)
        if (result > 0) {
            sqlDB.setTransactionSuccessful()
        }
        sqlDB.endTransaction()
        return result
    }

    // UPDATE
    fun updateMemo(num: Int, dto: MemoDto): Int {
        // ContentValues를 이용한 저장
        val contentValues = ContentValues()
        contentValues.put(MEMO_TITLE, dto.title)
        contentValues.put(MEMO_CONTENT, dto.content)
        contentValues.put(MEMO_DATE, dto.date)
        sqlDB.beginTransaction()
        val result = sqlDB.update(DATABASE_TABLE, contentValues, "_id=?", arrayOf(num.toString()))
        if (result > 0) {
            sqlDB.setTransactionSuccessful()
        }
        sqlDB.endTransaction()
        return result
    }

    // SELECT
    fun selectAllMemos(): MutableList<MemoDto> {
        val list = mutableListOf<MemoDto>()
        val cursor = sqlDB.rawQuery("select * from $DATABASE_TABLE", null)
        while (cursor.moveToNext()) {
            list.add(
                MemoDto(
                    title = cursor.getString(1),
                    content = cursor.getString(2),
                    date = cursor.getString(3)
                )
            )
        }
        return list
    }

    fun selectMemo(num: Int): MemoDto {
        Log.d("aaaaaa","$num ${selectAllMemos()}")
        val id= (num+1).toString()
        val query = "select * from $DATABASE_TABLE where _id=$id"

        val cursor = sqlDB.rawQuery(query,null)
        var result = MemoDto("","","")
        while (cursor.moveToNext()) {
            result = MemoDto(
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3)
            )
        }
        return result
    }

    // COUNT
    fun getCount(): Int {
        return 1
    }

    // DELETE
    fun deleteMemo(num: Int): Int {
        sqlDB.beginTransaction()
        val result = sqlDB.delete(DATABASE_TABLE, "_id=?", arrayOf((num+1).toString()))
        Log.d("aaaa", "deleteMemo: ${num} $result")
        if(result >0){
            sqlDB.setTransactionSuccessful()
        }
        sqlDB.endTransaction()
        return result
    }


    @Throws(SQLException::class)
    fun open() {
        helper = DBHelper(mCtx!!)
        sqlDB = helper.writableDatabase
    }

    fun dbOpenHelper(context: Context) {
        mCtx = context
    }

    fun create() {
        // DB생성

    }

    fun upgrade(oldVersion: Int, newVersion: Int) {
        // DB version 변경

    }

    fun close() {
        // DB종료

    }

    inner class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

        override fun onCreate(db: SQLiteDatabase) { // 테이블 생성

            // 테이블 생성 쿼리
            val query: String =
                "CREATE TABLE if not exists $DATABASE_TABLE ( _id integer primary key autoincrement, $MEMO_TITLE text, $MEMO_CONTENT text, $MEMO_DATE text);";
            db.execSQL(query)
        }

        // upgrade가 필요한 경우 기존 테이블 drop 후 onCreate로 새롭게 생성
        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            val sql = "DROP TABLE if exists $DATABASE_TABLE"
            db.execSQL(sql)
            onCreate(db)
        }
    }
}