package com.ssafy.memo

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.sql.SQLException

class MemoDao {

    // DB선언부
    private lateinit var helper: DBHelper
    private lateinit var sqlDB: SQLiteDatabase
    private var mCtx: Context? = null

    private val DATABASE_TABLE = "memos"
    private val NUM = "num"
    private val MEMO_TITLE = "memoTitle"
    private val MEMO_CONTENT = "memoContent"
    private val MEMO_DATE = "memoDate"

    // INSERT
    fun insertMemo(dto: MemoDto): Long {
        val args = ContentValues()
        args.put(MEMO_TITLE, dto.title)
        args.put(MEMO_CONTENT, dto.content)
        args.put(MEMO_DATE, dto.date)

        return sqlDB.insert(DATABASE_TABLE, null, args)
    }

    // UPDATE
    fun updateMemo(dto: MemoDto): Int {
        val args = ContentValues()
        args.put(MEMO_TITLE, dto.title)
        args.put(MEMO_CONTENT, dto.content)
        args.put(MEMO_DATE, dto.date)

        return sqlDB.update(DATABASE_TABLE, args, "$NUM = ?", arrayOf(dto.num.toString()))
    }

    // SELECT
    fun selectAllMemos(): MutableList<MemoDto> {
        val list = mutableListOf<MemoDto>()
        sqlDB.rawQuery("""
            SELECT $NUM, $MEMO_TITLE, $MEMO_CONTENT, $MEMO_DATE 
            FROM $DATABASE_TABLE
            """.trimIndent(), null).use {
            if (it.moveToFirst()) {
                do {
                    list.add(MemoDto(it.getInt(0), it.getString(1), it.getString(2), it.getLong(3)))
                } while (it.moveToNext())
            }
        }
        return list
    }

    fun selectMemo(num: Int): MemoDto {
        sqlDB.rawQuery("""
            SELECT $NUM, $MEMO_TITLE, $MEMO_CONTENT, $MEMO_DATE 
            FROM $DATABASE_TABLE 
            WHERE $NUM = ?
            """.trimIndent(), arrayOf(num.toString())).use {
            it.moveToFirst()
            return MemoDto(it.getInt(0), it.getString(1), it.getString(2), it.getLong(3))
        }
    }

    // COUNT
    fun getCount() : Int {
        sqlDB.rawQuery("""
            SELECT COUNT($NUM) 
            FROM $DATABASE_TABLE
            """.trimIndent(), null).use {
            if (it.moveToFirst()) {
                return it.getInt(0)
            }
            else {
                return 0
            }
        }
    }

    // DELETE
    fun deleteMemo(num : Int): Int {
        val result = sqlDB.delete(DATABASE_TABLE, "$NUM = ?" , arrayOf(num.toString())) ;
        return result
    }


    @Throws(SQLException::class)
    fun open() {
        helper = DBHelper(mCtx!!)
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
        // DB version 변경
        helper.onUpgrade(sqlDB, 1, 2)
    }

    fun close() {
        // DB종료
        sqlDB.close()
    }

    inner class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_TABLE, null, 1) {

        override fun onCreate(db: SQLiteDatabase?) { // 테이블 생성
            db!!.execSQL("""
                CREATE TABLE $DATABASE_TABLE (
                    $NUM INTEGER PRIMARY KEY AUTOINCREMENT, 
                    $MEMO_TITLE CHAR(20), 
                    $MEMO_CONTENT CHAR(200), 
                    $MEMO_DATE long
                );
                """.trimIndent()
            )
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db!!.execSQL("DROP TABLE IF EXISTS $DATABASE_TABLE")
            onCreate(db)
        }
    }
}