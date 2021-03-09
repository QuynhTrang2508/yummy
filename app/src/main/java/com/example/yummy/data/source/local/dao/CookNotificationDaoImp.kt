package com.example.yummy.data.source.local.dao

import android.annotation.SuppressLint
import com.example.yummy.data.model.CookNotification
import com.example.yummy.data.model.CookNotification.Companion.COOK_ID
import com.example.yummy.data.model.CookNotification.Companion.COOK_TABLE
import com.example.yummy.data.source.local.db.AppDatabase

class CookNotificationDaoImp private constructor(database: AppDatabase) : CookNotificationDao {

    private val writableDB = database.writableDatabase
    private val readableDB = database.readableDatabase

    @SuppressLint("Recycle")
    override fun getCookNotification(): List<CookNotification> {
        val cookNotificationList = mutableListOf<CookNotification>()
        val cursor = readableDB.query(
            COOK_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$COOK_ID DESC"
        )

        if (cursor.moveToFirst()) {
            do {
                val cookNotification = CookNotification(cursor)
                cookNotificationList.add(cookNotification)
            } while (cursor.moveToNext())
        }
        return cookNotificationList
    }

    override fun getLastCookNotification(): CookNotification {
        val query = "SELECT * FROM $COOK_TABLE ORDER BY $COOK_ID DESC LIMIT 1 "
        val cursor = readableDB.rawQuery(query, null)
        cursor.moveToFirst()
        return CookNotification(cursor)
    }

    override fun getItemCookNotification(idCook: Int): CookNotification {
        val cursor = readableDB.query(
            false, COOK_TABLE, null, "$COOK_ID = ?",
            arrayOf(idCook.toString()), null, null, null, null
        )
        return CookNotification(cursor)
    }

    override fun addCookNotification(cookNotification: CookNotification): Boolean {
        val result = writableDB.insert(
            COOK_TABLE,
            null,
            cookNotification.getContentValues()
        )
        return result > 0
    }

    override fun deleteCookNotification(idCook: Int): Boolean {
        return writableDB.delete(
            COOK_TABLE,
            "$COOK_ID= ?",
            arrayOf(idCook.toString())
        ) > 0
    }

    companion object {
        private var instance: CookNotificationDaoImp? = null

        fun getInstance(appDatabase: AppDatabase):
                CookNotificationDaoImp =
            instance ?: synchronized(this) {
                instance ?: CookNotificationDaoImp(appDatabase).also {
                    instance = it
                }
            }
    }
}
