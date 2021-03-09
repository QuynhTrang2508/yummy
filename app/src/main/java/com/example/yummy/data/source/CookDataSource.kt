package com.example.yummy.data.source

import com.example.yummy.data.model.CookNotification
import com.example.yummy.data.source.local.utils.OnDataLocalCallback

interface CookDataSource {
    fun getCookNotification(callback: OnDataLocalCallback<List<CookNotification>>)
    fun getLastNotification(callback: OnDataLocalCallback<CookNotification>)
    fun getItemCookNotification(idCook: Int, callback: OnDataLocalCallback<CookNotification>)
    fun addCookNotification(
        cookNotification: CookNotification,
        callback: OnDataLocalCallback<Boolean>
    )

    fun deleteCookNotification(idCook: Int, callback: OnDataLocalCallback<Boolean>)
}
