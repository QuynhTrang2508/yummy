package com.example.yummy.ui.meallist

import android.os.Parcelable
import android.view.View
import androidx.core.os.bundleOf
import com.example.yummy.R
import com.example.yummy.base.BaseFragment
import com.example.yummy.data.model.*
import com.example.yummy.ui.adapter.MealByOneAdapter
import com.example.yummy.ui.dialog.LoadingDialog
import com.example.yummy.ui.mealdetail.MealDetailFragment
import com.example.yummy.utlis.*
import kotlinx.android.synthetic.main.fragment_meal_list.*

class MealListFragment : BaseFragment(), MealListContract.View {

    private val mealAdapter = MealByOneAdapter(this::itemMealClicked)
    private var presenter: MealListPresenter? = null
    private var loadingDialog: LoadingDialog? = null

    override val layoutResource get() = R.layout.fragment_meal_list

    override fun setupViews() {
        initAdapter()
        initDialog()
    }

    override fun initData() {
        context?.let {
            val mealRepository = RepositoryUtils.getMealRepository(it)
            presenter = MealListPresenter(this, mealRepository)
        }
        presenter?.start()
        initInputData()
    }

    override fun initActions() {
        buttonBack.apply {
            increaseHitArea(DIMENSION_20)
            setOnClickListener {
                parentFragmentManager.removeFragment(
                    R.id.frameMain,
                    this@MealListFragment
                )
            }
        }
    }

    override fun showMeals(meals: List<Meal>) {
        mealAdapter.updateData(meals)
        recyclerMeals.startLayoutAnimation()
    }

    override fun showError(message: String) {
        context?.showToast(message)
    }

    override fun showLoading() {
        loadingDialog?.show()
    }

    override fun hideLoading() {
        loadingDialog?.dismiss()
    }

    private fun initAdapter() {
        recyclerMeals.adapter = mealAdapter
    }

    private fun initDialog() {
        context?.let {
            loadingDialog = LoadingDialog(it)
        }
    }

    private fun initInputData() {
        arguments?.getParcelable<Parcelable>(BUNDLE_MEAL).let { meal ->
            when {
                meal as? Category != null -> {
                    textDescription.text = meal.name
                    textDescriptionDetail.text = meal.description
                    presenter?.getMealsByCategory(meal)
                }
                meal as? Ingredient != null -> {
                    textDescription.text = meal.name
                    textDescriptionDetail.text =
                        if (!meal.description.equals("null")) meal.description else resources.getString(
                            R.string.text_update
                        )
                    presenter?.getMealsByIngredient(meal)
                }
                meal as? Area != null -> {
                    textDescription.visibility = View.GONE
                    textDescriptionDetail.visibility = View.GONE
                    viewInformationMeal.visibility = View.GONE
                    presenter?.getMealsByArea(meal)
                }
                else -> return
            }
        }
    }

    private fun itemMealClicked(meal: Meal) {
        parentFragmentManager.addFragment(R.id.frameMain, MealDetailFragment.getInstance(meal))
    }

    companion object {
        const val BUNDLE_MEAL = "BUNDLE_MEAL"
        const val DIMENSION_20 = 20f

        fun getInstance(component: Any) = MealListFragment().apply {
            arguments = bundleOf(BUNDLE_MEAL to component)
        }
    }
}
