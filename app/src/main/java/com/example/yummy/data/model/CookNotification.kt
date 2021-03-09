package com.example.yummy.data.model

import android.content.ContentValues
import android.database.Cursor
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CookNotification(
    val id: Int = 0,
    val date: String,
    val time: String
) : Parcelable {
    constructor(cursor: Cursor) : this(
        id = cursor.getInt(cursor.getColumnIndex(COOK_ID)),
        date = cursor.getString(cursor.getColumnIndex(COOK_DATE)),
        time = cursor.getString(cursor.getColumnIndex(COOK_TIME))
    )

    fun getContentValues() = ContentValues().apply {
        put(COOK_DATE, date)
        put(COOK_TIME, time)
    }

    companion object {
        const val COOK_TABLE = "cook"
        const val COOK_ID = "id"
        const val COOK_DATE = "date"
        const val COOK_TIME = "time"
    }
}
