package com.egco428.a13343.fortunecookies.DataSource


import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.egco428.a13343.fortunecookies.Helper.MySQLiteHelper
import com.egco428.a13343.fortunecookies.Model.Comment

//doing about DATA
class CommentDataSource(context: Context){
    // Database fields
    private var database: SQLiteDatabase? = null
    private val dbHelper: MySQLiteHelper
    private val allColumns = arrayOf<String>(MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_COMMENT, MySQLiteHelper.COLUMN_TYPE)

    // make sure to close the cursor
    val allComments: List<Comment>
        get() {
            val comments = ArrayList<Comment>()

            val cursor = database!!.query(MySQLiteHelper.TABLE_COMMENTS,
                allColumns, null, null, null, null, null)

            cursor.moveToFirst()
            while (!cursor.isAfterLast) {
                val comment = cursorToComment(cursor)
                comments.add(comment)
                cursor.moveToNext()
            }
            cursor.close()
            return comments
        }

    init {
        dbHelper = MySQLiteHelper(context)
    }

    @Throws(SQLException::class)
    fun open() {
        database = dbHelper.getWritableDatabase()
    }

    fun close() {
        dbHelper.close()
    }

    fun createComment(comment: String,type:String): Comment {
        val values = ContentValues()
        values.put(MySQLiteHelper.COLUMN_COMMENT, comment)
        values.put(MySQLiteHelper.COLUMN_TYPE,type)
        val insertId = database!!.insert(MySQLiteHelper.TABLE_COMMENTS, null,values)
        val cursor = database!!.query(MySQLiteHelper.TABLE_COMMENTS,allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null, null, null, null)
        cursor.moveToFirst()
        val newComment = cursorToComment(cursor)
        cursor.close()
        return newComment
    }

    fun deleteComment(comment: Comment) {
        val id = comment.id
        println("Comment deleted with id: " + id)
        database!!.delete(MySQLiteHelper.TABLE_COMMENTS, MySQLiteHelper.COLUMN_ID+ " = " + id, null)
    }

    fun getData(index:Int): Comment {
        var count:Int = 0
        var comment = Comment()
        val cursor = database!!.query(MySQLiteHelper.TABLE_COMMENTS,
            allColumns, null, null, null, null, null)
        cursor.moveToFirst()
        while (count<index) {
            count+=1
            cursor.moveToNext()
        }
        if(count == index){
            comment = cursorToComment(cursor)
        }
        cursor.close()
        return comment
    }
    private fun cursorToComment(cursor: Cursor): Comment {
        val comment = Comment()
        comment.id = cursor.getLong(0)
        comment.commentData = cursor.getString(1)
        comment.commentType = cursor.getString(2)
        return comment
    }
}
