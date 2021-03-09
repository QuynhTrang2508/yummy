package com.example.yummy.data.source.local

import com.example.yummy.data.model.CookNotification
import com.example.yummy.data.source.CookDataSource
import com.example.yummy.data.source.local.dao.CookNotificationDao
import com.example.yummy.data.source.local.utils.LocalAsyncTask
import com.example.yummy.data.source.local.utils.OnDataLocalCallback

class CookLocalDataSource private constructor(
    private val cookNotificationDao: CookNotificationDao
) : CookDataSource {

    override fun getCookNotification(callback: OnDataLocalCallback<List<CookNotification>>) {
        LocalAsyncTask<Unit, List<CookNotification>>(callback) {
            cookNotificationDao.getCookNotification()
        }.execute(Unit)
    }

    override fun getLastNotification(callback: OnDataLocalCallback<CookNotification>) {
        LocalAsyncTask<Unit, CookNotification>(callback) {
            cookNotificationDao.getLastCookNotification()
        }.execute(Unit)
    }

    override fun getItemCookNotification(
        idCook: Int,
        callback: OnDataLocalCallback<CookNotification>
    ) {
        LocalAsyncTask<Int, CookNotification>(callback) {
            cookNotificationDao.getItemCookNotification(idCook)
        }.execute(idCook)
    }

    override fun addCookNotification(
        cookNotification: CookNotification,
        callback: OnDataLocalCallback<Boolean>
    ) {
        LocalAsyncTask<CookNotification, Boolean>(callback) {
            cookNotificationDao.addCookNotification(cookNotification)
        }.execute(cookNotification)
    }

    override fun deleteCookNotification(idCook: Int, callback: OnDataLocalCallback<Boolean>) {
        LocalAsyncTask<Int, Boolean>(callback) {
            cookNotificationDao.deleteCookNotification(idCook)
        }.execute(idCook)
    }

    companion object {
        private var instance: CookLocalDataSource? = null
        fun getInstance(cookNotificationDao: CookNotificationDao) =
            instance ?: CookLocalDataSource(cookNotificationDao).also { instance = it }
    }
}
