package com.flaringapp.smadlab6.presentation.fragments.home.models

import com.flaringapp.smadlab6.presentation.fragments.home.HomeContract

data class IntervalViewModel(
    override val interval: String,
    override val average: String,
    override val frequency: String
): HomeContract.IIntervalViewModel