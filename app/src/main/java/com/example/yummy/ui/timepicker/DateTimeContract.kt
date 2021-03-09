package com.example.yummy.ui.timepicker

import com.example.yummy.base.BasePresenter
import com.example.yummy.base.BaseView
import com.example.yummy.data.model.CookNotification

interface DateTimeContract {
    interface View : BaseView {
        fun showNotifications(data: List<CookNotification>)
        fun addAlarm(cookNotification: CookNotification)
        fun cancelAlarm(cookNotification: CookNotification)
    }

    interface Presenter : BasePresenter {
        fun getNotifications()
        fun addNotification(cookNotification: CookNotification)
        fun deleteNotification(cookNotification: CookNotification)
        fun getLastNotification()
    }
}
