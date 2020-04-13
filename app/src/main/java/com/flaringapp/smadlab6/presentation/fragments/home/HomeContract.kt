package com.flaringapp.smadlab6.presentation.fragments.home

import com.flaringapp.smadlab6.presentation.mvp.IBaseFragment
import com.flaringapp.smadlab6.presentation.mvp.IBasePresenter

interface HomeContract {

    interface ViewContract: IBaseFragment {
        fun initInput1(input: String)
        fun initInput2(input: String)
        fun initInput3(input: String)
        fun initInput4(input: String)

        fun setInput1Error(error: Int?)
        fun setInput2Error(error: Int?)
        fun setInput3Error(error: Int?)
        fun setInput4Error(error: Int?)

        fun setIntergroupVariance(result: String)
        fun setResidualVariance(result: String)
        fun setTotalVariance(result: String)
        fun setFisherCriterion(result: String)
    }

    interface PresenterContract: IBasePresenter<ViewContract> {
        fun onInput1(input: String)
        fun onInput2(input: String)
        fun onInput3(input: String)
        fun onInput4(input: String)
    }

    interface IIntervalViewModel {
        val interval: String
        val average: String
        val frequency: String
    }

}