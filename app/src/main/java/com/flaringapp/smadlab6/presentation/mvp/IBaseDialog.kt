package com.flaringapp.smadlab6.presentation.mvp

interface IBaseDialog: IBaseView {
    val dialogTag: String?

    fun close()
}