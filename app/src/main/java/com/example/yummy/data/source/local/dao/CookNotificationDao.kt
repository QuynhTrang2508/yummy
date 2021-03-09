package com.example.yummy.data.source.local.dao

import com.example.yummy.data.model.CookNotification

interface CookNotificationDao {
    fun getCookNotification(): List<CookNotification>
    fun getLastCookNotification(): CookNotification
    fun getItemCookNotification(idCook: Int): CookNotification
    fun addCookNotification(cookNotification: CookNotification): Boolean
    fun deleteCookNotification(idCook: Int): Boolean
}
