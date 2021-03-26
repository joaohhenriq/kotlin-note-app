package com.joaohhenriq.kotlin_note_app.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class DbManager {
    private val dbName = "NotesDB"
    private val dbTable = "NotesTable"
    private val colID = "ID"
    private val colName = "Title"
    private val colDesc = "Description"
    val dbVersion = 1
    val sqlCreateTable = "CREATE TABLE IF NOT EXISTS $dbTable ($colID INTEGER PRIMARY KEY, $colName TEXT, $colDesc TEXT);"
    private var sqlDB: SQLiteDatabase? = null

    constructor(context: Context){
        val db = DataBaseHelperNotes(context)
        sqlDB = db.writableDatabase
    }

    inner class DataBaseHelperNotes: SQLiteOpenHelper {
        var context: Context
        constructor(context: Context):super(context, dbName, null, dbVersion){
            this.context = context
        }

        override fun onCreate(db: SQLiteDatabase?) {
            db?.execSQL(sqlCreateTable)
            Toast.makeText(this.context, "database created", Toast.LENGTH_LONG).show()
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db?.execSQL("DROP TABLE IF EXISTS $dbTable")
        }
    }

    fun insert(values: ContentValues): Long {
        val id = sqlDB?.insert(dbTable, "", values)
        return id!!
    }
}