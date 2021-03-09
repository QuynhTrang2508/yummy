package com.example.yummy.data.source.local.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.yummy.data.model.Meal.Companion.FAVORITE_KEY_ID
import com.example.yummy.data.model.Meal.Companion.FAVORITE_KEY_IMAGE
import com.example.yummy.data.model.Meal.Companion.FAVORITE_KEY_NAME
import com.example.yummy.data.model.Meal.Companion.FAVORITE_TABLE_NAME
import com.example.yummy.data.model.Meal.Companion.FAVORITE_TIME_LONG
import com.example.yummy.data.model.Note

class AppDatabase private constructor(
    context: Context?,
    dbName: String,
    version: Int
) : SQLiteOpenHelper(context, dbName, null, version) {

    override fun onCreate(database: SQLiteDatabase?) {
        database?.apply {
            execSQL(CREATE_NOTE_TABLE)
            execSQL(CREATE_FAVORITE_TABLE)
        }
    }

    override fun onUpgrade(database: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        database?.apply {
            execSQL(DROP_NOTE_TABLE)
            execSQL(DROP_FAVORITE_TABLE)
        }
        onCreate(database)
    }

    companion object {
        private const val DB_NAME = "yummy.db"
        private const val DB_VERSION = 1

        private const val CREATE_NOTE_TABLE =
            "CREATE TABLE ${Note.NOTE_TABLE} (" +
                    "${Note.NOTE_ID} INTEGER PRIMARY KEY, " +
                    "${Note.NOTE_TITLE} INT, " +
                    "${Note.NOTE_DESCRIPTION} INT, " +
                    "${Note.NOTE_DATE} Long )"

        private const val DROP_NOTE_TABLE = "DROP TABLE IF EXISTS " + Note.NOTE_TABLE

        private val CREATE_FAVORITE_TABLE = String.format(
            "CREATE TABLE %s (%s TEXT PRIMARY KEY,  %s TEXT, %s TEXT, %s TEXT)",
            FAVORITE_TABLE_NAME,
            FAVORITE_KEY_ID,
            FAVORITE_KEY_NAME,
            FAVORITE_KEY_IMAGE,
            FAVORITE_TIME_LONG
        )

        private val DROP_FAVORITE_TABLE =
            String.format("DROP TABLE IF EXISTS %s", FAVORITE_TABLE_NAME)

        private val lock = Any()
        private var instance: AppDatabase? = null
        fun getInstance(context: Context?) =
            instance ?: synchronized(lock) {
                instance ?: AppDatabase(context, DB_NAME, DB_VERSION).also { instance = it }
            }
    }
}
