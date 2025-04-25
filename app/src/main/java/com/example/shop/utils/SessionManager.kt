package com.example.shop.utils

import android.content.Context
import java.sql.Timestamp


class SessionManager(context: Context) {

    private val sharedPref = context.getSharedPreferences("product_session", Context.MODE_PRIVATE)

    fun setLastDownload(timeStamp: Long) {
        val editor = sharedPref.edit()
        editor.putLong("LAST_DOWNLOAD",timeStamp)
        editor.apply()
    }

    fun getLastDownload(): Long{
        return sharedPref.getLong("LAST_DOWNLOAD", 0)!!
    }
}