package com.egco428.a13343.fortunecookies.Helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log


class MySQLiteHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(database: SQLiteDatabase) {
        database.execSQL(DATABASE_CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Log.w(MySQLiteHelper::class.java!!.name,
            "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data")
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS)
        onCreate(db)
    } //drop table that you want to delete by upgrade version

    companion object {

        val TABLE_COMMENTS = "comments" //table name
        val COLUMN_ID = "_id"  //column ID
        val COLUMN_COMMENT = "comment" //column name

        private val DATABASE_NAME = "commments.db"
        private val DATABASE_VERSION = 1 //version that you can change

        // Database creation sql statement
        private val DATABASE_CREATE = ("create table "
                + TABLE_COMMENTS + "(" + COLUMN_ID
                + " integer primary key autoincrement, " + COLUMN_COMMENT
                + " text not null);")
    }
}