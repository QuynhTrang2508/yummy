package com.example.yummy.ui.timepicker

import com.example.yummy.R
import com.example.yummy.base.BaseDialogFragment

class DialogAcceptFragment(private val onClickChoose: () -> Unit) : BaseDialogFragment() {

    override val title get() = resources.getString(R.string.title_notification)
    override val content get() = resources.getString(R.string.title_cook_time_question)

    override fun clickYes() {
        onClickChoose()
        dialog?.dismiss()
    }

    override fun clickNo() {
        onClickChoose
        dialog?.dismiss()
    }
}
