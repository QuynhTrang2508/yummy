package com.example.yummy.ui.timepicker

import com.example.yummy.R
import com.example.yummy.base.BaseFragment
import com.example.yummy.ui.mealdetail.MealDetailFragment
import com.example.yummy.utlis.increaseHitArea
import com.example.yummy.utlis.removeFragment
import kotlinx.android.synthetic.main.fragment_date_notification.*

class DateTimeFragment : BaseFragment() {
    override val layoutResource get() = R.layout.fragment_date_notification

    override fun setupViews() {

    }

    override fun initData() {

    }

    override fun initActions() {
        buttonBackDateTime.apply {
            increaseHitArea(MealDetailFragment.DIMENSION_20)
            setOnClickListener {
                parentFragmentManager.removeFragment(
                    R.id.frameMain,
                    this@DateTimeFragment
                )
            }
        }
    }

    companion object {
        const val DIMENSION_20 = 20f
    }
}
