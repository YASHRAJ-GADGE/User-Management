package com.yash.usermanagementapp.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.yash.usermanagementapp.user.User

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context,DATABASE_NAME, null, DATABASE_VERSION)
{
    companion object
    {
        private const val DATABASE_NAME = "users.db"
       // private const val DATABASE_VERSION = 1
        private const val DATABASE_VERSION = 2
        const val TABLE_NAME = "users"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_AGE = "age"
        const val COLUMN_NUMBER="number"
    }
    override fun onCreate(db: SQLiteDatabase)
    {
        val createTable = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_NAME TEXT," +
                " $COLUMN_AGE INTEGER," +
                "$COLUMN_NUMBER INTEGER  UNIQUE)"
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
//        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
//        onCreate(db)

        if (oldVersion < 2) {
        db.execSQL("ALTER TABLE $TABLE_NAME ADD COLUMN $COLUMN_NUMBER INTEGER UNIQUE")
////        db.execSQL("""CREATE TABLE temp_users (
////                id INTEGER PRIMARY KEY AUTOINCREMENT,
////                name TEXT,
////                age INTEGER,
////                number INTEGER UNIQUE)""")
//
//        // Copy data from old table to new temporary table
//        db.execSQL("""INSERT INTO temp_users (id, name, age, number)
//                SELECT id, name, age, number FROM $TABLE_NAME""")
//
//        // Drop the old table
//        db.execSQL("DROP TABLE $TABLE_NAME")
//
//        // Rename the new temporary table to the original table name
//        db.execSQL("ALTER TABLE temp_users RENAME TO $TABLE_NAME")
//    }
        }
    }
    fun addUser(name: String, age: Int , number : Int):Boolean
    {
        val db = this.writableDatabase
        return try {
            val values = ContentValues().apply {
                put(COLUMN_NAME, name)
                put(COLUMN_AGE, age)
                put(COLUMN_NUMBER, number)
            }
            db.insertOrThrow(TABLE_NAME, null, values)
            true
        } catch (e: SQLiteConstraintException) {
            false
        } finally {
           // db.close()
        }
    }
//    fun getAllUsers(): List<User> {
//        val users = mutableListOf<User>()
//        val db = this.readableDatabase
//        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
//
//        if (cursor.moveToFirst()) {
//            do {
//                val user = User(
//                    id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
//                    name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
//                    age = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_AGE))
//                )
//                users.add(user)
//            } while (cursor.moveToNext())
//        }
//        cursor.close()
//        //db.close()
//        return users
//    }

    //for adding add and delete buttons in item

fun getAllUsers():List<User>
{
    val users = mutableListOf<User>()
  val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

   while (cursor.moveToNext())
   {
                    val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                    val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
                    val age = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_AGE))
                    val number=cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NUMBER))

        val user=User(id,name,age,number)
        users.add(user)
    }
    cursor.close()
   // db.close()
    return users
}

    fun updateUser(userId: Int, name: String, age: Int, number: Int): Boolean
    {
        val db = this.writableDatabase
        return try{
        val values = ContentValues().apply{
            put(COLUMN_NAME, name)
            put(COLUMN_AGE, age)
            put(COLUMN_NUMBER,number)
        }
        val rowsAffected = db.update(TABLE_NAME, values, "$COLUMN_ID=?", arrayOf(userId.toString()))
        rowsAffected>0
    }catch (e: SQLiteConstraintException){

        false

    }
    finally {
        //db.close()
    }
    }
    fun deleteUser(userId: Int): Int
    {
        val db = this.writableDatabase
        val rowsDeleted = db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(userId.toString()))
     //   db.close()  // Always close the database after operations
        return rowsDeleted
    }
}