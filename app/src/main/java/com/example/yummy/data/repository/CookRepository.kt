package com.example.yummy.data.repository

import com.example.yummy.data.model.CookNotification
import com.example.yummy.data.source.CookDataSource
import com.example.yummy.data.source.local.utils.OnDataLocalCallback

class CookRepository private constructor(
    private val local: CookDataSource
) : CookDataSource {
    override fun getCookNotification(callback: OnDataLocalCallback<List<CookNotification>>) {
        local.getCookNotification(callback)
    }

    override fun getLastNotification(callback: OnDataLocalCallback<CookNotification>) {
        local.getLastNotification(callback)
    }

    override fun getItemCookNotification(
        idCook: Int,
        callback: OnDataLocalCallback<CookNotification>
    ) {
        local.getItemCookNotification(idCook, callback)
    }

    override fun addCookNotification(
        cookNotification: CookNotification,
        callback: OnDataLocalCallback<Boolean>
    ) {
        local.addCookNotification(cookNotification, callback)
    }

    override fun deleteCookNotification(idCook: Int, callback: OnDataLocalCallback<Boolean>) {
        local.deleteCookNotification(idCook, callback)
    }

    companion object {
        private var instance: CookRepository? = null
        fun getInstance(local: CookDataSource) =
            instance ?: CookRepository(local).also { instance = it }
    }
}
