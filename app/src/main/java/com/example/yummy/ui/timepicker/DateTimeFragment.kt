package com.example.yummy.ui.timepicker

import android.os.Build
import android.view.View
import android.widget.DatePicker
import android.widget.TimePicker
import com.example.yummy.R
import com.example.yummy.base.BaseFragment
import com.example.yummy.data.model.CookNotification
import com.example.yummy.ui.dialog.LoadingDialog
import com.example.yummy.utlis.*
import kotlinx.android.synthetic.main.date_picker.*
import kotlinx.android.synthetic.main.fragment_date_notification.*
import kotlinx.android.synthetic.main.time_picker.*
import java.text.DateFormat
import java.util.*

class DateTimeFragment : BaseFragment(), DateTimeContract.View, View.OnClickListener,
    DatePicker.OnDateChangedListener,
    TimePicker.OnTimeChangedListener {

    private val calendar = Calendar.getInstance()
    private var presenter: DateTimePresenter? = null
    private var dialog: LoadingDialog? = null

    override val layoutResource get() = R.layout.fragment_date_notification

    override fun setupViews() {
        initDialog()
    }

    override fun initData() {
        val context = context ?: return
        val repository = RepositoryUtils.getCookRepository(context)
        presenter = DateTimePresenter(this, repository)
        presenter?.start()
    }

    override fun initActions() {
        buttonDateOk.setOnClickListener(this)
        buttonDateCancel.setOnClickListener(this)
        buttonTimeOk.setOnClickListener(this)
        buttonTimeCancel.setOnClickListener(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            datePicker.apply {
                setOnDateChangedListener(this@DateTimeFragment)
                minDate = calendar.time.time
            }
        }

        timePicker.apply {
            setIs24HourView(true)
            setOnTimeChangedListener(this@DateTimeFragment)
        }

        buttonBackDateTime.apply {
            increaseHitArea(DIMENSION_20)
            setOnClickListener {
                parentFragmentManager.removeFragment(
                    R.id.frameMain,
                    this@DateTimeFragment
                )
            }
        }
    }

    override fun showNotifications(data: List<CookNotification>) {

    }

    override fun addAlarm(cookNotification: CookNotification) {
        imageClock.visibility = View.VISIBLE
        textCongratulations.visibility = View.VISIBLE
        textChooseTime.visibility = View.GONE
        context?.let {
            AlarmManagerUtil.create(it, cookNotification.id, calendar.timeInMillis)
        }
    }

    override fun cancelAlarm(cookNotification: CookNotification) {
        context?.let {
            AlarmManagerUtil.cancel(it)
        }
    }

    override fun showError(message: String) {
        context?.showToast(message)
    }

    override fun showLoading() {
        dialog?.show()
    }

    override fun hideLoading() {
        dialog?.dismiss()
    }

    override fun onClick(v: View?) {
        when (v) {
            buttonDateOk -> setDateOk()
            buttonDateCancel -> setDateCancel()
            buttonTimeOk -> setTimeOk()
            buttonTimeCancel -> setTimeCancel()
        }
    }

    override fun onDateChanged(view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        calendar.apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, monthOfYear)
            set(Calendar.DAY_OF_MONTH, dayOfMonth)
            set(Calendar.YEAR, year)
        }
    }

    override fun onTimeChanged(view: TimePicker?, hourOfDay: Int, minute: Int) {
        calendar.apply {
            set(Calendar.HOUR_OF_DAY, hourOfDay)
            set(Calendar.MINUTE, minute)
        }
    }

    private fun initDialog() {
        context?.let { dialog = LoadingDialog(it) }
    }

    private fun setDateOk() {
        includeDate.visibility = View.GONE
        includeTime.visibility = View.VISIBLE
        textChooseTime.visibility = View.VISIBLE
        textDate.text = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.time)
    }

    private fun setDateCancel() {
        includeDate.visibility = View.VISIBLE
        includeTime.visibility = View.GONE
    }

    private fun setTimeOk() {
        textTime.text = DateFormat.getTimeInstance().format(calendar.time)
        openDialogCookNotification()
    }

    private fun setTimeCancel() {
        includeDate.visibility = View.VISIBLE
        textDate.apply {
            DateFormat.getDateInstance(DateFormat.FULL).format(calendar.time)
        }
        includeTime.visibility = View.GONE
    }

    private fun openDialogCookNotification() {
        DialogAcceptFragment(this::clickedSaveNotification).show(
            parentFragmentManager,
            null
        )
    }

    private fun clickedSaveNotification() {
        includeTime.visibility = View.GONE
        val cookNotification =
            CookNotification(
                date = textDate.text.toString(),
                time = textTime.text.toString()
            )
        presenter?.addNotification(cookNotification)
    }

    companion object {
        const val DIMENSION_20 = 20f
    }
}

