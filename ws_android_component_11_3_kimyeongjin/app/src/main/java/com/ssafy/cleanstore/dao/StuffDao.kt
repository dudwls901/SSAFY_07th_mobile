package com.ssafy.cleanstore.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.ssafy.cleanstore.dto.Stuff
import java.sql.SQLException

class StuffDao {
    // DB선언부
    lateinit var helper: DBHelper
    lateinit var sqlDB: SQLiteDatabase
    private var mCtx: Context? = null

    private val DB_NAME = "clean_store"
    private val TABLE_NAME = "Stuff"
    private val STUFF_ID = "_id"
    private val STUFF_NAME = "name"
    private val STUFF_CNT = "count"
    private val STUFF_DATE = "date"

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

    // 물품 등록
    fun stuffInsert(stuff: Stuff): Long{
        val args = ContentValues()
        args.put(STUFF_NAME, stuff.name)
        args.put(STUFF_CNT, stuff.count)
        args.put(STUFF_DATE, stuff.regDate)
        return sqlDB.insert(TABLE_NAME, null, args)
    }

    // 특정 물품 조회 method
    fun stuffSelect(stuffId: Int): Stuff? {
        sqlDB.rawQuery("SELECT * FROM $TABLE_NAME WHERE id = $stuffId",null).use{
            return if(it.moveToFirst())
                Stuff(it.getInt(0), it.getString(1), it.getInt(2), it.getString(3))
            else
                null
        }
    }

    // 물품 조회 method
    fun stuffSelectAll(): MutableList<Stuff> {
        val list: ArrayList<Stuff> = arrayListOf()
        sqlDB.rawQuery("SELECT $STUFF_ID, $STUFF_NAME, $STUFF_CNT, $STUFF_DATE FROM $TABLE_NAME",null).use{
            if(it.moveToFirst()){
                do{
                    list.add(Stuff(it.getInt(0), it.getString(1), it.getInt(2), it.getString(3)))
                }while(it.moveToNext())
            }
        }
        return list
    }

    // 물품정보 변경
    fun stuffUpdate(stuff: Stuff): Int{
        val args = ContentValues()
        args.put(STUFF_NAME, stuff.name)
        args.put(STUFF_CNT, stuff.count)
        args.put(STUFF_DATE, stuff.regDate)
        return sqlDB.update(TABLE_NAME, args, "$STUFF_ID = ?", arrayOf(stuff.id.toString()))
    }

    // 물품 삭제 method
    fun stuffDelete(stuffId: Int): Int{
        return sqlDB.delete(TABLE_NAME, "$STUFF_ID = ?", arrayOf(stuffId.toString()))
    }

    inner class DBHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, 4) {
        
        // 테이블 생성
        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL("""CREATE TABLE if not exists $TABLE_NAME (
                    $STUFF_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                    $STUFF_NAME VARCHAR(50),
                    $STUFF_CNT INTEGER,
                    $STUFF_DATE TEXT
                )
                """)
        }

        override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) { //테이블 삭제 후 생성
            db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
            onCreate(db)
        }
    }
}