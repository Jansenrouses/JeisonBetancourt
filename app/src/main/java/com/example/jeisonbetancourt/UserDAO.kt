package com.example.jeisonbetancourt

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class UserDAO(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "user_database.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_NAME = "usuarios"
        const val COLUMN_ID = "id"
        const val COLUMN_USERNAME = "nombre_usuario"
        const val COLUMN_PASSWORD = "contraseña"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_USERNAME TEXT, $COLUMN_PASSWORD TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addUser(user: User): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_USERNAME, user.nombreusuario)
            put(COLUMN_PASSWORD, user.contrasena)
        }
        val id = db.insert(TABLE_NAME, null, values)
        db.close()
        return id
    }
    fun getUserByUsername(username: String): User? {
        val db = this.readableDatabase
        val cursor = db.query(TABLE_NAME, arrayOf(COLUMN_ID, COLUMN_USERNAME, COLUMN_PASSWORD),
            "$COLUMN_USERNAME=?", arrayOf(username), null, null, null)
        return if (cursor != null && cursor.moveToFirst()) {
            val user = User(
                cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)), // Cambiar getInt a getLong
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD))
            )
            cursor.close()
            user
        } else {
            null
        }
    }

    fun getUserByUsernameAndPassword(username: String, password: String): User? {
        val db = this.readableDatabase
        val cursor = db.query(TABLE_NAME, arrayOf(COLUMN_ID, COLUMN_USERNAME, COLUMN_PASSWORD),
            "$COLUMN_USERNAME=? AND $COLUMN_PASSWORD=?", arrayOf(username, password), null, null, null)
        return if (cursor != null && cursor.moveToFirst()) {
            val user = User(
                cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)), // Cambiar getInt a getLong
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD))
            )
            cursor.close()
            user
        } else {
            null
        }
    }

    fun deleteUser(user: User) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_ID=?", arrayOf(user.id.toString())) // Cambiar user.id a user.id.toString()
        db.close()
    }

    fun updateUser(user: User) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_USERNAME, user.nombreusuario)
            put(COLUMN_PASSWORD, user.contrasena)
        }
        db.update(TABLE_NAME, values, "$COLUMN_ID=?", arrayOf(user.id.toString())) // Cambiar user.id a user.id.toString()
        db.close()
    }
}
