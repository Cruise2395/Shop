package com.example.shop.utils

import com.example.shop.data.Product
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseManager(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "products.db"
        const val DATABASE_VERSION = 1

        private const val SQL_CREATE_TABLE_PRODUCTS =
            "CREATE TABLE ${Product.TABLE_NAME} (" +
                    "${Product.COLUMN_NAME_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "${Product.COLUMN_NAME_TITLE} TEXT," +
                    "${Product.COLUMN_NAME_IMAGE} TEXT)"

        private const val SQL_DROP_TABLE_PRODUCTS = "DROP TABLE IF EXISTS ${Product.TABLE_NAME}"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_PRODUCTS)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onDestroy(db)
        onCreate(db)
    }

    private fun onDestroy(db: SQLiteDatabase) {
        db.execSQL(SQL_DROP_TABLE_PRODUCTS)
    }
}