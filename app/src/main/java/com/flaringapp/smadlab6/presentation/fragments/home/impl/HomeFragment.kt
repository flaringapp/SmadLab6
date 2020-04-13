package com.flaringapp.smadlab6.presentation.fragments.home.impl

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import com.flaringapp.smadlab6.R
import com.flaringapp.smadlab6.presentation.fragments.home.HomeContract
import com.flaringapp.smadlab6.presentation.mvp.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.scope.currentScope

class HomeFragment : BaseFragment<HomeContract.PresenterContract>(), HomeContract.ViewContract {

    companion object {

        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override val presenter: HomeContract.PresenterContract by currentScope.inject()

    override fun onInitPresenter() {
        presenter.view = this
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initViews() {
        inputNumbers1.doAfterTextChanged { presenter.onInput1(it.toString()) }
        inputNumbers2.doAfterTextChanged { presenter.onInput2(it.toString()) }
        inputNumbers3.doAfterTextChanged { presenter.onInput3(it.toString()) }
        inputNumbers4.doAfterTextChanged { presenter.onInput4(it.toString()) }
    }

    override fun initInput1(input: String) {
        inputNumbers1.setText(input)
    }

    override fun initInput2(input: String) {
        inputNumbers2.setText(input)
    }

    override fun initInput3(input: String) {
        inputNumbers3.setText(input)
    }

    override fun initInput4(input: String) {
        inputNumbers4.setText(input)
    }

    override fun setInput1Error(error: Int?) {
        layoutInputNumbers1.error = error?.let { getString(it) }
    }

    override fun setInput2Error(error: Int?) {
        layoutInputNumbers2.error = error?.let { getString(it) }
    }

    override fun setInput3Error(error: Int?) {
        layoutInputNumbers3.error = error?.let { getString(it) }
    }

    override fun setInput4Error(error: Int?) {
        layoutInputNumbers4.error = error?.let { getString(it) }
    }

    override fun setIntergroupVariance(result: String) {
        textIntergroupVariance.text = result
    }

    override fun setResidualVariance(result: String) {
        textResidualVariance.text = result
    }

    override fun setTotalVariance(result: String) {
        textTotalVariance.text = result
    }

    override fun setFisherCriterion(result: String) {
        textFisherCriterion.text = result
    }
}