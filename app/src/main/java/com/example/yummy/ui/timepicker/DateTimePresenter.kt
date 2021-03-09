package com.example.yummy.ui.timepicker

import com.example.yummy.data.model.CookNotification
import com.example.yummy.data.repository.CookRepository
import com.example.yummy.data.source.local.utils.OnDataLocalCallback
import java.lang.Exception

class DateTimePresenter(
    private val view: DateTimeContract.View,
    private val repository: CookRepository
) : DateTimeContract.Presenter {

    override fun getNotifications() {
        repository.getCookNotification(object : OnDataLocalCallback<List<CookNotification>> {
            override fun onSuccess(data: List<CookNotification>) {
                view.showNotifications(data)
            }

            override fun onFail(exception: Exception) {

            }
        })
    }

    override fun addNotification(cookNotification: CookNotification) {
        repository.addCookNotification(cookNotification, object : OnDataLocalCallback<Boolean> {
            override fun onSuccess(data: Boolean) {
                if (data) {
                    getLastNotification()
                }
            }

            override fun onFail(exception: Exception) {
                view.showError(exception.message.toString())
            }
        })
    }

    override fun deleteNotification(cookNotification: CookNotification) {
        repository.deleteCookNotification(cookNotification.id,
            object : OnDataLocalCallback<Boolean> {
                override fun onSuccess(data: Boolean) {
                    if (data) {
                        getLastNotification()
                    }
                    return
                }

                override fun onFail(exception: Exception) {
                    view.showError(exception.message.toString())
                }
            })
    }

    override fun getLastNotification() {
        repository.getLastNotification(object : OnDataLocalCallback<CookNotification> {
            override fun onSuccess(data: CookNotification) {
                view.addAlarm(data)
            }

            override fun onFail(exception: Exception) {
                view.showError(exception.message.toString())
            }
        })
    }

    override fun start() {

    }
}
