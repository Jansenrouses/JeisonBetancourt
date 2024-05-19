package com.example.jeisonbetancourt

import DatabaseHelper
import android.content.Context
import android.content.ContentValues
import android.database.Cursor

class UserDAO(context: Context) {

    private val dbHelper = DatabaseHelper(context)

    fun addUser(user: User): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_USERNAME, user.nombreusuario)
            put(DatabaseHelper.COLUMN_PASSWORD, user.contrasena)
        }
        val id = db.insert(DatabaseHelper.TABLE_NAME, null, values)
        db.close()
        return id
    }

    fun getUser(username: String): User? {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.query(
            DatabaseHelper.TABLE_NAME,
            arrayOf(DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_USERNAME, DatabaseHelper.COLUMN_PASSWORD),
            "${DatabaseHelper.COLUMN_USERNAME} = ?",
            arrayOf(username),
            null,
            null,
            null,
            "1"
        )
        val idIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_ID)
        val usernameIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_USERNAME)
        val passwordIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_PASSWORD)

        var user: User? = null

        if (cursor.moveToFirst()) {
            if (idIndex != -1 && usernameIndex != -1 && passwordIndex != -1) {
                val id = cursor.getLong(idIndex)
                val username = cursor.getString(usernameIndex)
                val password = cursor.getString(passwordIndex)

                user = User(id, username, password)
            }
        }

        cursor.close()
        db.close()

        return user
        }
    }
